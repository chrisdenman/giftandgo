package com.giftandgo.assessment.service_history_repository

import com.giftandgo.assessment.service_history_uc_api.ServiceHistoryRepository
import org.springframework.data.repository.CrudRepository

@Suppress("unused")
interface SpringServiceHistoryRepository : CrudRepository<ServiceHistoryEntity, Long>, ServiceHistoryRepository
