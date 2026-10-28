// ❌ Controller does the work — no shared transaction
@PostMapping("/transfer")
void transfer(@RequestBody Transfer req) {
    accounts.debit(req.from(), req.amount());
    accounts.credit(req.to(), req.amount()); // credit can fail after debit
}

// ✅ Service owns the rule + transaction boundary
@Service
class TransferService {
    @Transactional
    public void transfer(Long from, Long to, BigDecimal amount) {
        accounts.debit(from, amount);
        accounts.credit(to, amount); // both commit, or both roll back
    }
}
