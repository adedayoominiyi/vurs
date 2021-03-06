// Modified or written by Ex Machina SAGL for inclusion with lambdaj.
// Copyright (c) 2009 Mario Fusco.
// Licensed under the Apache License, Version 2.0 (the "License")

package ch.lambdaj.function.closure;

import static ch.lambdaj.function.closure.ClosuresFactory.*;

import java.lang.reflect.*;

import ch.lambdaj.proxy.*;

/**
 * @author Mario Fusco
 */
class ProxyClosure extends InvocationInterceptor {

    private boolean registered = false;

	private final AbstractClosure closure;

	ProxyClosure(AbstractClosure closure) {
		this.closure = closure;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (registered) return method.invoke(proxy, args);
        registered = true;
		closure.bindInvocation(method, args);

        DelayedClosure.call();

        Class<?> returnType = method.getReturnType();
        if (returnType == Void.TYPE) {
            closure.closeUnhandeledInvocations();
            return null;
        }
		return createProxyClosure(closure, returnType);
	}
}
