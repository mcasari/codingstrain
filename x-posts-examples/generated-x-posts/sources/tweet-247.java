@Transactional
public void transfer(Long from, Long to, BigDecimal amount) {
    accounts.debit(from, amount);
    accounts.credit(to, amount);   // both commit, or both roll back
}
