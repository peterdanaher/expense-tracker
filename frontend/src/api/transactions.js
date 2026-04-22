import axios from "axios";

const API_BASE_URL = "http://localhost:8080/api/transactions";

export const getTransactions = async () => {
    const response = await axios.get(API_BASE_URL);
    return response.data;
};

export const createTransaction = async (transaction) => {
    const response = await axios.post(API_BASE_URL, transaction);
    return response.data;
};

export const deleteTransaction = async (id) => {
    await axios.delete(`${API_BASE_URL}/${id}`);
};