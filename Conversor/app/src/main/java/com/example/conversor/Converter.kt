package com.example.conversor

fun convertValue(input: String, inputBase: BaseType, outputBase: BaseType): String {
    if (!isValidInput(input, inputBase)) return "Número ${inputBase.label.lowercase()} inválido"

    val decimalValue = input.toInt(inputBase.base)
    val steps = StringBuilder().apply {
        append(showConversionToDecimalSteps(input, decimalValue, inputBase))
    }

    return when (outputBase) {
        BaseType.DECIMAL -> "$steps\nResultado: $input (${inputBase.label.lowercase()}) = $decimalValue (decimal)"
        inputBase -> input // Conversão para mesma base
        else -> formatConversionSteps(steps, decimalValue, outputBase.base, outputBase.label)
    }
}

private fun showConversionToDecimalSteps(input: String, decimal: Int, baseType: BaseType): String {
    val steps = StringBuilder("Passos para converter de ${baseType.label.lowercase()} para decimal:\n")
    var baseMultiplier = 1

    input.reversed().forEach { char ->
        val value = char.toString().toIntOrNull() ?: char.uppercaseChar() - 'A' + 10
        steps.append("   $char × $baseMultiplier = ${value * baseMultiplier}\n")
        baseMultiplier *= baseType.base
    }
    steps.append("   Resultado: $input em ${baseType.label.lowercase()} = $decimal em decimal\n")
    return steps.toString()
}

private fun formatConversionSteps(steps: StringBuilder, decimal: Int, base: Int, baseName: String): String {
    steps.append("2. Converta decimal para $baseName:\n")
    var n = decimal
    val result = StringBuilder()

    while (n > 0) {
        val remainder = n % base
        result.insert(0, if (base == 16 && remainder >= 10) ('A' + remainder - 10) else remainder.toString())
        steps.append("   $n ÷ $base = ${n / base} (resto: $remainder)\n")
        n /= base
    }
    return "$steps\nResultado: $result ($baseName)"
}

private fun isValidInput(input: String, baseType: BaseType) = when (baseType) {
    BaseType.BINARY -> input.matches("[01]+".toRegex())
    BaseType.OCTAL -> input.matches("[0-7]+".toRegex())
    BaseType.DECIMAL -> input.matches("\\d+".toRegex())
    BaseType.HEXA -> input.matches("[0-9A-Fa-f]+".toRegex())
}

enum class BaseType(val label: String, val base: Int) {
    BINARY("Binário", 2),
    OCTAL("Octal", 8),
    DECIMAL("Decimal", 10),
    HEXA("Hexadecimal", 16);
}
