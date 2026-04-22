function SummaryCard({ totals }) {
  return (
    <div className="summary-grid">
      <div className="card summary-card">
        <h3>Income</h3>
        <p>${totals.income.toFixed(2)}</p>
      </div>

      <div className="card summary-card">
        <h3>Expenses</h3>
        <p>${totals.expense.toFixed(2)}</p>
      </div>

      <div className="card summary-card">
        <h3>Balance</h3>
        <p>${totals.balance.toFixed(2)}</p>
      </div>
    </div>
  );
}

export default SummaryCard;