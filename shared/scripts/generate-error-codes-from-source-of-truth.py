import json
from pathlib import Path
import os

PROJECT_ROOT_PATH = Path(
    "/".join([os.path.dirname(os.path.realpath(__file__)), "..", ".."])
).resolve()

INPUT_FILE_PATH = PROJECT_ROOT_PATH / "shared" / "source-of-truth" / "error-codes.json"

TS_TEMPLATE_PATH = (
    PROJECT_ROOT_PATH / "shared" / "template" / "generatedTSTypeTemplate.txt"
)
TS_OUTPUT_PATH = PROJECT_ROOT_PATH / "FE" / "src" / "generated" / "ErrorCode.d.ts"

JAVA_TEMPLATE_PATH = (
    PROJECT_ROOT_PATH / "shared" / "template" / "generatedJavaClassTemplate.txt"
)
JAVA_OUTPUT_PATH = (
    PROJECT_ROOT_PATH
    / "BE"
    / "src"
    / "main"
    / "java"
    / "com"
    / "example"
    / "demo_springboot_api"
    / "generated"
    / "ErrorCode.java"
)

TEMPLATE_VALUE_INSERT_KEY = "${GENERATED_VALUES}"

# ======================
# Utils
# ======================


def load_json(path):
    with open(path, "r", encoding="utf-8") as f:
        return json.load(f)


def indent(text, level=1):
    return "\n".join(("  " * level) + line if line else "" for line in text.split("\n"))


# ======================
# TypeScript Generation
# ======================


def flatten_ts(node, path=None):
    if path is None:
        path = ["errors"]

    result = []

    if isinstance(node, list):
        for item in node:
            result.append(".".join(path + [item]))

    elif isinstance(node, dict):
        for key, value in node.items():
            result.extend(flatten_ts(value, path + [key]))

    return result


def generate_ts_type(error_codes):
    lines = ["export type ErrorCode ="]

    for code in error_codes:
        lines.append(f'  | "{code}"')

    lines.append("")
    return "\n".join(lines)


# ======================
# Java Generation
# ======================


def generate_enum(name, values, level):
    lines = [f"public enum {name} {{"]

    for i, v in enumerate(values):
        comma = "," if i < len(values) - 1 else ";"
        lines.append(f"  {v}{comma}")

    lines.append("}")
    return indent("\n".join(lines), level)


def generate_class(name, body, level):
    lines = [f"public class {name} {{", body, "}"]
    return indent("\n".join(lines), level)


def walk_java(node, level=1):
    parts = []

    for key, value in node.items():
        if isinstance(value, list):
            parts.append(generate_enum(key, value, level))
        elif isinstance(value, dict):
            inner = walk_java(value, level + 1)
            parts.append(generate_class(key, inner, level))

    return "\n\n".join(parts)


def generate_java(data):
    return walk_java(data)


# ======================
# Template Handling
# ======================


def apply_template(template_str, generated_code):
    if TEMPLATE_VALUE_INSERT_KEY not in template_str:
        raise ValueError(
            "Template must contain the insert key: " + TEMPLATE_VALUE_INSERT_KEY
        )
    return template_str.replace(TEMPLATE_VALUE_INSERT_KEY, generated_code)


# ======================
# Main
# ======================


def main():
    print("=== GENERATE TYPES BASED ON SOURCE OF TRUTH UTIL ===")
    print(f"- Source: {INPUT_FILE_PATH}")
    print(f"- TS type template: {TS_TEMPLATE_PATH}")
    print(f"- Java class template: {JAVA_TEMPLATE_PATH}")

    print()
    print("> GENERATING VALUES")

    data = load_json(INPUT_FILE_PATH)

    # ---- TypeScript ----
    error_codes = sorted(set(flatten_ts(data)))
    ts_body = generate_ts_type(error_codes)
    ts_template = TS_TEMPLATE_PATH.read_text(encoding="utf-8")
    ts_output = apply_template(ts_template, ts_body)

    # ---- Java ----
    java_body = generate_java(data)
    java_template = JAVA_TEMPLATE_PATH.read_text(encoding="utf-8")
    java_output = apply_template(java_template, java_body)

    print("> DONE")
    print()

    print("The outputs will be written to these locations:")
    print(TS_OUTPUT_PATH)
    print(JAVA_OUTPUT_PATH)

    while True:
        choice = input(
            "Confirm writing the outputs, cancel the operation, or view the outputs? (Yes/No/View | Y/N/V): "
        )
        if choice.startswith("Y") or choice.startswith("y"):
            TS_OUTPUT_PATH.write_text(ts_output, encoding="utf-8")
            JAVA_OUTPUT_PATH.write_text(java_output, encoding="utf-8")
            print("✅ Generated:")
            print(f" - {TS_OUTPUT_PATH}")
            print(f" - {JAVA_OUTPUT_PATH}")
            break
        elif choice.startswith("N") or choice.startswith("n"):
            print("Cancelled generate util, outputs discarded.")
            break
        elif choice.startswith("V") or choice.startswith("v"):
            print(f"- {TS_OUTPUT_PATH}:")
            print(ts_output)
            print()
            print(f"- {JAVA_OUTPUT_PATH}:")
            print(java_output)
        else:
            print("Unknown choice")


if __name__ == "__main__":
    main()
