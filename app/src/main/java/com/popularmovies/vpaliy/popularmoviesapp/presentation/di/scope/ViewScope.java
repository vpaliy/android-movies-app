package com.popularmovies.vpaliy.popularmoviesapp.presentation.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * A component that has this scope will exist as long as a view does.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewScope {
}
