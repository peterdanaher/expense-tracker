function TransactionList({ transactions, onDeleteTransaction }) {
  if (transactions.length === 0) {
    return (
      <div className="card">
        <h2>Transactions</h2>
        <p>No transactions yet</p>
      </div>
    );
  }

  return (
    <div className="card">
      <h2>Transactions</h2>
      <table className="transaction-table">
        <thead>
          <tr>
            <th>Type</th>
            <th>Amount</th>
            <th>Category</th>
            <th>Date</th>
            <th>Description</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          {transactions.map((transaction) => (
            <tr key={transaction.id}>
              <td>{transaction.type}</td>
              <td>${Number(transaction.amount).toFixed(2)}</td>
              <td>{transaction.category}</td>
              <td>{transaction.date}</td>
              <td>{transaction.description}</td>
              <td>
                <button
                  className="delete-btn"
                  onClick={() => onDeleteTransaction(transaction.id)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default TransactionList;