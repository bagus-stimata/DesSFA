package com.erp.distribution.sfa.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.erp.distribution.sfa.data.source.local.dao.CocktailDao
import com.erp.distribution.sfa.data.source.local.entity.CocktailEntity
import com.erp.distribution.sfa.data.source.local.entity.asCocktailList
import com.erp.distribution.sfa.data.source.local.entity.asDrinkList
import com.erp.distribution.sfa.data.source.local.entity.asFavoriteEntity
import com.erp.distribution.sfa.presentation.base.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

/**
 * Created by Gastón Saillén on 10 August 2020
 */

@ExperimentalCoroutinesApi
class GetCocktailUseCase_Asal @Inject constructor(private val cocktailDao: CocktailDao) {
    suspend fun saveFavoriteCocktail(cocktailEntity: CocktailEntity) {
        return cocktailDao.saveFavoriteCocktail(cocktailEntity.asFavoriteEntity())
    }

    fun getFavoritesCocktails(): LiveData<List<CocktailEntity>> {
        return cocktailDao.getAllFavoriteDrinksWithChanges().map { it.asDrinkList() }
    }

    suspend fun deleteCocktail(cocktailEntity: CocktailEntity) {
        return cocktailDao.deleteFavoriteCoktail(cocktailEntity.asFavoriteEntity())
    }

    suspend fun saveCocktail(cocktailEntity: CocktailEntity) {
        cocktailDao.saveCocktail(cocktailEntity)
    }

    suspend fun getCachedCocktails(cocktailName: String): Resource<List<CocktailEntity>> {
        return Resource.Success(cocktailDao.getCocktails(cocktailName).asCocktailList())
    }

    suspend fun isCocktailFavorite(cocktailEntity: CocktailEntity): Boolean {
        return cocktailDao.getCocktailById(cocktailEntity.cocktailId) != null
    }
}