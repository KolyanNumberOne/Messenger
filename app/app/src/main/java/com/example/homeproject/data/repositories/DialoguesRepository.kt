package com.example.homeproject.data.repositories

import com.example.homeproject.data.datamodels.Dialogues
import com.example.homeproject.data.datasources.DialoguesDataSource
import javax.inject.Inject

interface DialoguesRepository{
    suspend fun getDialogues(id: Int): List<Dialogues>
}
class DialoguesRepositoryImp @Inject constructor(
    private val dataSource: DialoguesDataSource
): DialoguesRepository {
    override suspend fun getDialogues(id: Int) = dataSource.getDialogues(id)
}