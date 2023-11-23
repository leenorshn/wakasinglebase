package com.innov.wakasinglebase.data.mapper

import com.innov.wakasinglebase.data.model.ProductModel
import com.innov.wakasinglebase.data.model.UserModel
import com.wakabase.MyProductsQuery
import com.wakabase.ProductQuery


fun MyProductsQuery.MyProduct .toThreadModel():ProductModel{
    return ProductModel(
        id=id,
        title=title,
        theme=theme,
        coverImage=coverImage,
        price=price,
        createdAt = createdAt.toLong(),
        isArchived = isArchived,
        author = UserModel(
            uid=author.id,
            name=author.name,
            profilePic = author.profilePic

        )
    )
}



fun ProductQuery.Product.toThreadModel():ProductModel{
    return ProductModel(
        id=id,
        title=title,
        theme=theme,
        coverImage=coverImage,
        price=price,
        createdAt = createdAt.toLong(),
        isArchived = isArchived,
        author = UserModel(
            uid=author.id,
            name=author.name,
            profilePic = author.profilePic

        )
    )
}