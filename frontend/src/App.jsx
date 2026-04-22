import { useEffect, useMemo, useState } from 'react'
import { getTransactions, createTransaction, deleteTransaction } from "./api/transactions";
import TransactionForm from "./components/TransactionForm";
import TransactionList from "./components/TransactionList";
import SummaryCard from "./components/SummaryCard";
import "./index.css";

function App() {
  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const loadTransactions = async () => {
    try {
      setLoading(true);
      setError("");
      const data = await getTransactions();
      setTransactions(data);
    } catch (err) {
      setError("Could not load transactions");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadTransactions();
  }, []);

  const handleAddTransaction = async (newTransaction) => {
    try {
      setError("");
      const savedTransaction = await createTransaction(newTransaction);
      setTransactions((prev) => [...prev, savedTransaction]);
    } catch (err) {
      setError("Could not save transaction");
      console.error(err);
    }
  };

  const handleDeleteTransaction = async (id) => {
    try {
      setError("");
      await deleteTransaction(id);
      setTransactions((prev) => prev.filter((transaction => transaction.id !== id)));
    } catch (err) {
      setError("Could not delete transaction");
      console.error(err);
    }
  };

  const totals = useMemo(() => {
    const income = transactions
      .filter((t) => t.type.toLowerCase() === "income")
      .reduce((sum, t) => sum + Number(t.amount), 0);
    
    const expense = transactions
      .filter((t) => t.type.toLowerCase() === "expense")
      .reduce((sum, t) => sum + Number(t.amount), 0);
    
    return {
      income,
      expense,
      balance: income - expense,
    };
  }, [transactions]);

  return (
    <div className="app">
      <div className="container">
        <h1>Expense Tracker</h1>

        {error && <p className="error">{error}</p>}

        <SummaryCard totals={totals} />

        <TransactionForm onAddTransaction={handleAddTransaction} />

        {loading ? (
          <p>Loading transactions...</p>
        ) : (
          <TransactionList
            transactions={transactions}
            onDeleteTransaction={handleDeleteTransaction}
          />
        )}
      </div>
    </div>
  )
}

export default App
