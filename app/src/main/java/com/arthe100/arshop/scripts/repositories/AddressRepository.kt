package com.arthe100.arshop.scripts.repositories

import com.arthe100.arshop.models.Address
import com.arthe100.arshop.scripts.mvi.base.ProfileState
import com.arthe100.arshop.scripts.mvi.base.ViewState
import com.arthe100.arshop.scripts.network.services.AddressService
import javax.inject.Inject

class AddressRepository @Inject constructor(private val service: AddressService){
    suspend fun getAll(address: Address): ViewState{
        return try {
            return ProfileState.GetAddressesSuccess(service.getAll(address))
        }catch (t: Throwable){ ViewState.Failure(t)}
    }

    suspend fun get(id: Long): ViewState {
        return try {
            return ProfileState.GetAddressSuccess(service.get(id))
        }catch (t: Throwable){ ViewState.Failure(t)}
    }

    suspend fun create(address: Address) : ViewState{
        return try {
            return ProfileState.CreateAddressSuccess(service.create(address))
        }catch (t: Throwable){ ViewState.Failure(t)}
    }

    suspend fun update(id: Long) : ViewState{
        return try {
            return ProfileState.UpdateAddressSuccess(service.update(id))
        }catch (t: Throwable){ ViewState.Failure(t)}
    }

    suspend fun delete(id: Long) : ViewState{
        return try {
            service.delete(id)
            return ProfileState.DeleteAddressSuccess
        }catch (t: Throwable){ ViewState.Failure(t)}
    }

}