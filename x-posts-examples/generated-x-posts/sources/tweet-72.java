enum DataScope {
    CUSTOMERS_READ,
    CUSTOMERS_WRITE,
    CUSTOMERS_UPDATE,
    CUSTOMERS_DELETE,
    ORDERS_READ,
    ORDERS_WRITE,
    ORDERS_UPDATE,
    ORDERS_DELETE,
    PRODUCTS_READ,
    PRODUCTS_WRITE,
    PRODUCTS_UPDATE,
    PRODUCTS_DELETE,
    INVENTORY_READ,
    INVENTORY_WRITE,
    INVENTORY_UPDATE,
    INVENTORY_DELETE,
    PAYMENTS_READ,
    PAYMENTS_WRITE,
    PAYMENTS_UPDATE,
    PAYMENTS_DELETE,
    SHIPMENTS_READ,
    SHIPMENTS_WRITE,
    SHIPMENTS_UPDATE,
    SHIPMENTS_DELETE,
    INVOICES_READ,
    INVOICES_WRITE,
    INVOICES_UPDATE,
    INVOICES_DELETE,
    REPORTS_READ,
    REPORTS_WRITE,
    REPORTS_UPDATE,
    REPORTS_DELETE,
    USERS_READ,
    USERS_WRITE,
    USERS_UPDATE,
    USERS_DELETE,
    ROLES_READ,
    ROLES_WRITE,
    ROLES_UPDATE,
    ROLES_DELETE,
    AUDIT_READ,
    AUDIT_WRITE,
    AUDIT_UPDATE,
    AUDIT_DELETE,
    CONFIG_READ,
    CONFIG_WRITE,
    CONFIG_UPDATE,
    CONFIG_DELETE,
}

// 48 flags — EnumSet: bit vector (~48 bits)
// HashSet: 48 entries + buckets + boxing overhead

// Build the full set
EnumSet<DataScope> admin = EnumSet.allOf(DataScope.class);
Set<DataScope> adminHash = new HashSet<>(Arrays.asList(DataScope.values()));

// Bulk change — bitwise OR/AND on a few machine words
EnumSet<DataScope> ordersDomain = EnumSet.of(
    DataScope.ORDERS_READ, DataScope.ORDERS_WRITE,
    DataScope.ORDERS_UPDATE, DataScope.ORDERS_DELETE);
admin.removeAll(ordersDomain);

EnumSet<DataScope> readOnly = EnumSet.copyOf(admin);
readOnly.removeIf(p -> !p.name().endsWith("_READ"));

// contains() — O(1) bit test, not hash + bucket walk
boolean ok = admin.contains(DataScope.PAYMENTS_READ);
