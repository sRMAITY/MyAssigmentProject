package com.assignmentapplication.module

import com.assignmentapplication.data.repository.AuthRepository
import com.assignmentapplication.data.repository.AuthRepositoryImpl
import com.assignmentapplication.data.repository.UserListREpository
import com.assignmentapplication.data.repository.UserListRepoImpl
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

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun providesAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    @Singleton
    fun provideFirestore() = FirebaseDatabase.getInstance()

    @Provides
    fun providesUserListREpository(impl : UserListRepoImpl) : UserListREpository = impl


  //  @Provides
  //  fun providesAppAuthRepository() : AuthRepository =
}