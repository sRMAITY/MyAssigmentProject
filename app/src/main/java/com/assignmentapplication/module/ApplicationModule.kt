package com.assignmentapplication.module

import android.content.Context
import com.assignmentapplication.data.repository.AuthRepository
import com.assignmentapplication.data.repository.AuthRepositoryImpl
import com.assignmentapplication.data.repository.UserListREpository
import com.assignmentapplication.data.repository.UserListRepoImpl
import com.assignmentapplication.utils.AppSheardPreference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    // Dependency For firebase Auth
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

     // AuthRepository
    @Provides
    fun providesAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

     // Firebase Database
    @Provides
    @Singleton
    fun provideFirestore() = FirebaseDatabase.getInstance()

     // user List Repository Impl Dependency
    @Provides
    fun providesUserListREpository(impl : UserListRepoImpl) : UserListREpository = impl

}