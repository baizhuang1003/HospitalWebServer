package com.tianyuan.core;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface EntityMapper<T> {
	
	public T Mapper(ResultSet rs) throws SQLException;
	
}