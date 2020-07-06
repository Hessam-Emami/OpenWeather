package com.emami.openweather.app.di.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.emami.openweather.app.di.scope.ActivityScope
import javax.inject.Inject
import javax.inject.Provider

/**
 * Enables constructor injection with fragments,
 * Since we're planning to follow Dependent Component Dependency in future modules,
 * Each module that has an android Activity must initialize this in it's own graph(component)
 */
@ActivityScope
class OpenWeatherFragmentFactory @Inject constructor(
    private val fragmentProviders: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<Fragment>>
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return fragmentProviders[classLoader.loadClass(className)]?.get() ?: super.instantiate(
            classLoader,
            className
        )
    }
}