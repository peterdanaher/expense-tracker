import { useState } from "react";

const INITIAL_FORM_STATE = {
  type: "expense",
  amount: "",
  category: "",
  date: "",
  description: "",
};

function TransactionForm({ onAddTransaction }) {
  const [formData, setFormData] = useState(INITIAL_FORM_STATE);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const today = new Date().toISOString().split('T')[0];

    const transactionToSubmit = {
      ...formData,
      amount: Number(formData.amount),
      date: formData.date || today,
    };

    await onAddTransaction(transactionToSubmit);

    setFormData({ ...INITIAL_FORM_STATE });
  };

  return (
    <form className="card form" onSubmit={handleSubmit}>
      <h2>Add Transaction</h2>

      <label>
        Type{' '}
        <select name="type" value={formData.type} onChange={handleChange}>
          <option value="expense">Expense</option>
          <option value="income">Income</option>
        </select>
      </label>

      <label>
        Amount{' '}
        <input
          type="number"
          name="amount"
          step="0.01"
          min="0.01"
          value={formData.amount}
          onChange={handleChange}
          required
        />
      </label>

      <label>
        Category{' '}
        <input
          type="text"
          name="category"
          value={formData.category}
          onChange={handleChange}
          required
        />
      </label>

      <label>
        Date{' '}
        <input
          type="date"
          name="date"
          value={formData.date}
          onChange={handleChange}
        />
      </label>

      <label>
        Description{' '}
        <input
          type="text"
          name="description"
          value={formData.description}
          onChange={handleChange}
          maxLength="255"
        />
      </label>

      <button type="submit">Add Transaction</button>
    </form>
  );
}

export default TransactionForm;