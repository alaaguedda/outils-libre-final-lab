"""
Integration tests for PricingEngine.
Windows-compatible version.
"""

import subprocess
import os
import sys
import pytest

JAVA_DRIVER = """
import com.lab.pricing.*;
import java.util.List;

public class IntegrationDriver {
    public static void main(String[] args) {
        PricingEngine engine = new PricingEngine();

        OrderSummary s1 = engine.calculateOrder(List.of(100.0), List.of(1), "REGULAR", null);
        System.out.println("T1_FINAL=" + s1.getFinalPrice());

        OrderSummary s2 = engine.calculateOrder(List.of(100.0), List.of(1), "REGULAR", "SAVE10");
        System.out.println("T2_FINAL=" + s2.getFinalPrice());

        OrderSummary s3 = engine.calculateOrder(List.of(100.0), List.of(1), "VIP", null);
        System.out.println("T3_FINAL=" + s3.getFinalPrice());

        OrderSummary s4 = engine.calculateOrder(List.of(100.0), List.of(1), "VIP", "SAVE20");
        System.out.println("T4_FINAL=" + s4.getFinalPrice());

        OrderSummary s5 = engine.calculateOrder(List.of(10.0, 5.0, 20.0), List.of(3, 4, 1), "REGULAR", null);
        System.out.println("T5_SUBTOTAL=" + s5.getSubtotal());
        System.out.println("T5_FINAL="    + s5.getFinalPrice());
    }
}
"""

IS_WINDOWS = sys.platform == "win32"
GRADLEW    = "gradlew.bat" if IS_WINDOWS else "./gradlew"
CP_SEP     = ";" if IS_WINDOWS else ":"


def build_project():
    project_root = os.path.abspath(os.path.join(os.path.dirname(__file__), ".."))
    result = subprocess.run(
        [GRADLEW, "build", "-x", "test"],
        capture_output=True, text=True, cwd=project_root, shell=IS_WINDOWS
    )
    assert result.returncode == 0, f"Build failed:\n{result.stderr}"


def run_driver():
    project_root = os.path.abspath(os.path.join(os.path.dirname(__file__), ".."))
    driver_path  = os.path.join(project_root, "IntegrationDriver.java")
    class_path   = os.path.join(project_root, "build", "classes", "java", "main")

    with open(driver_path, "w") as f:
        f.write(JAVA_DRIVER)

    compile_result = subprocess.run(
        ["javac", "-cp", class_path, driver_path],
        capture_output=True, text=True, cwd=project_root
    )
    assert compile_result.returncode == 0, f"Compile failed:\n{compile_result.stderr}"

    classpath = f".{CP_SEP}{class_path}"
    run_result = subprocess.run(
        ["java", "-cp", classpath, "IntegrationDriver"],
        capture_output=True, text=True, cwd=project_root
    )
    assert run_result.returncode == 0, f"Run failed:\n{run_result.stderr}"

    os.remove(driver_path)
    os.remove(os.path.join(project_root, "IntegrationDriver.class"))

    return run_result.stdout


def parse_output(output):
    result = {}
    for line in output.strip().splitlines():
        if "=" in line:
            key, val = line.split("=", 1)
            result[key.strip()] = float(val.strip())
    return result


@pytest.fixture(scope="module")
def results():
    build_project()
    output = run_driver()
    return parse_output(output)


def test_regular_no_discount(results):
    assert abs(results["T1_FINAL"] - 110.0) < 0.01

def test_save10(results):
    assert abs(results["T2_FINAL"] - 99.0) < 0.01

def test_vip_no_code(results):
    assert abs(results["T3_FINAL"] - 91.8) < 0.01

def test_vip_save20(results):
    assert abs(results["T4_FINAL"] - 70.2) < 0.01

def test_multiple_items_subtotal(results):
    assert abs(results["T5_SUBTOTAL"] - 70.0) < 0.01

def test_multiple_items_final(results):
    assert abs(results["T5_FINAL"] - 77.0) < 0.01