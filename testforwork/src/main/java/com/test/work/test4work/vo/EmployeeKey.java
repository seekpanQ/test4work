package com.test.work.test4work.vo;

import java.util.Objects;

public final class EmployeeKey {
    private final String department;
    private final int employeeId;

    public EmployeeKey(String department, int employeeId) {
        this.department = department;
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeKey that = (EmployeeKey) o;
        return employeeId == that.employeeId &&
                Objects.equals(department, that.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(department, employeeId);
    }

    // Getter 方法...
}