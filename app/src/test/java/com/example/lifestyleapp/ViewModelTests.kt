package com.example.lifestyleapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import org.mockito.Mock
import com.example.lifestyleapp.UsersViewModel
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.spy
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4ClassRunner::class)
class ViewModelTests {

    @Mock
    private lateinit var viewModel: UsersViewModel
    @Mock
    private lateinit var liveData: LiveData<User>
    @Mock
    private lateinit var observer: Observer<User>
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule();

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = spy(UsersViewModel((ApplicationProvider.getApplicationContext())))
        viewModel.initActiveUser("Alejandro")
        liveData = viewModel.selected


    }

    @Test
    fun `a test`(){
        assertNotNull(viewModel.selected)
    }

}