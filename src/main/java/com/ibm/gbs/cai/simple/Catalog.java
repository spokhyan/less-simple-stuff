package com.ibm.gbs.cai.simple;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/less-simple")
public class Catalog extends Application{
	
	Set<Class<?>> paths = new HashSet<Class<?>>();

	public Catalog()
	{
		paths.add(Plan.class);
	}
	
	@Override
	public Set<Class<?>> getClasses() {
		// TODO Auto-generated method stub
		return paths;
	}

}
