package com.brayandev.movilboxapp.data.utils

import com.brayandev.movilboxapp.domain.model.ProductModel

fun filterListAndOrder(
    list: List<ProductModel>,
    searchText: String,
    orderBy: SortByName,
    category: String = "",
): List<ProductModel> {
    return list.filter {
        it.title.contains(
            searchText,
            ignoreCase = true,
        ) && (category.isBlank() || it.category.equals(category, ignoreCase = true))
    }.let { applySortItem(it, orderBy) }
}

enum class SortByName(val displayName: String) {
    CATEGORY_ALPHABETICALLY("Categoría alfabéticamente"),
    PRICE_LOW_TO_HIGH("Precios de barato a caro"),
    PRICE_HIGH_TO_LOW("Precio de mayor a menor"),
    DISCOUNT_HIGH_TO_LOW("Descuento de mayor a menor"),
    RATING_HIGH_TO_LOW("Calificación de alto a bajo"),
    STOCK("Existencias"),
    BRAND("Marca"),
}

fun applySortItem(products: List<ProductModel>, sortBy: SortByName): List<ProductModel> {
    return when (sortBy) {
        SortByName.PRICE_LOW_TO_HIGH -> {
            products.sortedBy { it.price }
        }

        SortByName.PRICE_HIGH_TO_LOW -> {
            products.sortedByDescending { it.price }
        }

        SortByName.DISCOUNT_HIGH_TO_LOW -> {
            products.sortedByDescending { it.discountPercentage }
        }

        SortByName.RATING_HIGH_TO_LOW -> {
            products.sortedByDescending { it.rating }
        }

        SortByName.STOCK -> {
            products.sortedByDescending { it.stock }
        }

        SortByName.BRAND -> {
            products.sortedBy { it.brand }
        }

        SortByName.CATEGORY_ALPHABETICALLY -> {
            products.sortedBy { it.category }
        }
    }
}

inline fun <reified T : Enum<T>> EnumValues(): List<T> = enumValues<T>().toList()

fun parcelStringToEnum(string: String): SortByName {
    return when (string) {
        "Categoría alfabéticamente" -> SortByName.CATEGORY_ALPHABETICALLY
        "Precios de barato a caro" -> SortByName.PRICE_LOW_TO_HIGH
        "Precio de mayor a menor" -> SortByName.PRICE_HIGH_TO_LOW
        "Descuento de mayor a menor" -> SortByName.DISCOUNT_HIGH_TO_LOW
        "Calificación de alto a bajo" -> SortByName.RATING_HIGH_TO_LOW
        "Existencias" -> SortByName.STOCK
        "Marca" -> SortByName.BRAND
        else -> SortByName.CATEGORY_ALPHABETICALLY
    }
}
