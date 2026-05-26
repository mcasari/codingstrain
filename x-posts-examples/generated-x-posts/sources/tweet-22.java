// Decouple services: save to DB, then publish.
//
// OrderService saves → publishEvent(OrderCreatedEvent).
//
// Listeners react without tight coupling.
