// ❌ Magic strings — typos compile, transitions scattered
String status = "DRAFT";
if (status.equals("DRAFT")) {
    status = "SENT"; // who else may set "SNT"?
}

// ✅ Enum owns states + transitions
enum OrderStatus {
    DRAFT {
        OrderStatus next() { return SENT; }
    },
    SENT {
        OrderStatus next() { return DELIVERED; }
    },
    DELIVERED {
        OrderStatus next() { return DELIVERED; }
    };

    abstract OrderStatus next();
}

OrderStatus status = OrderStatus.DRAFT.next(); // SENT
