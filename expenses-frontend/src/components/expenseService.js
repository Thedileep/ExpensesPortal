import axios from "axios";

const API_URL = "https://expensesportalbackend.onrender.com";

export const getExpenses = async () => {

    const token = localStorage.getItem("token");

    const response = await axios.get(
        `${API_URL}/expenses`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return response.data;
};


export const addExpense = async (expense) => {

    const token = localStorage.getItem("token");

    const response = await axios.post(
        `${API_URL}/expenses`,
        expense,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return response.data;
};


export const deleteExpense = async (id) => {

    const token = localStorage.getItem("token");

    await axios.delete(
        `${API_URL}/expenses/${id}`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );
};