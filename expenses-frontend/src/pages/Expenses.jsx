import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
    getExpenses,
    addExpense,
    deleteExpense
} from "../components/expenseService";

function Expenses() {

    const navigate = useNavigate();

    const [expenses, setExpenses] = useState([]);

    const [formData, setFormData] = useState({
        amount: "",
        category: "FOOD",
        description: "",
        expenseDate: "",
        paymentMethod: "UPI"
    });

    const [error, setError] = useState("");

    const username = localStorage.getItem("username");

    useEffect(() => {
        loadExpenses();
    }, []);

    const loadExpenses = async () => {

        try {

            const data = await getExpenses();

            setExpenses(data);

        } catch (err) {

            console.error(err);

            if (err.response?.status === 401) {
                logout();
            }
        }
    };

    const handleChange = (e) => {

        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        setError("");

        try {

            await addExpense({
                ...formData,
                amount: Number(formData.amount)
            });

            setFormData({
                amount: "",
                category: "FOOD",
                description: "",
                expenseDate: "",
                paymentMethod: "UPI"
            });

            loadExpenses();

        } catch (err) {

            console.error(err);

            setError("Unable to add expense");
        }
    };

    const handleDelete = async (id) => {

        try {

            await deleteExpense(id);

            loadExpenses();

        } catch (err) {

            console.error(err);
        }
    };

    const logout = () => {

        localStorage.removeItem("token");
        localStorage.removeItem("username");
        localStorage.removeItem("role");

        navigate("/login");
    };

    const totalExpense = expenses.reduce(
        (total, expense) =>
            total + Number(expense.amount),
        0
    );

    return (
        <div className="dashboard">

            <nav className="navbar">

                <h2>Expenses Portal</h2>

                <div>
                    <span>
                        Welcome, {username}
                    </span>

                    <button onClick={logout}>
                        Logout
                    </button>
                </div>

            </nav>


            <div className="dashboard-content">

                <div className="summary-card">

                    <h3>Total Expenses</h3>

                    <h1>
                        ₹{totalExpense.toFixed(2)}
                    </h1>

                </div>


                <div className="expense-form">

                    <h2>Add Expense</h2>

                    <form onSubmit={handleSubmit}>

                        <input
                            type="number"
                            name="amount"
                            placeholder="Amount"
                            value={formData.amount}
                            onChange={handleChange}
                            required
                        />


                        <select
                            name="category"
                            value={formData.category}
                            onChange={handleChange}
                        >
                            <option value="FOOD">
                                Food
                            </option>

                            <option value="TRAVEL">
                                Travel
                            </option>

                            <option value="SHOPPING">
                                Shopping
                            </option>

                            <option value="BILLS">
                                Bills
                            </option>

                            <option value="OTHER">
                                Other
                            </option>

                        </select>


                        <input
                            type="text"
                            name="description"
                            placeholder="Description"
                            value={formData.description}
                            onChange={handleChange}
                            required
                        />


                        <input
                            type="date"
                            name="expenseDate"
                            value={formData.expenseDate}
                            onChange={handleChange}
                            required
                        />


                        <select
                            name="paymentMethod"
                            value={formData.paymentMethod}
                            onChange={handleChange}
                        >

                            <option value="UPI">
                                UPI
                            </option>

                            <option value="CASH">
                                Cash
                            </option>

                            <option value="CARD">
                                Card
                            </option>

                            <option value="NET_BANKING">
                                Net Banking
                            </option>

                        </select>


                        <button type="submit">
                            Add Expense
                        </button>

                    </form>

                    {error && (
                        <p className="error">
                            {error}
                        </p>
                    )}

                </div>


                <div className="expense-list">

                    <h2>My Expenses</h2>

                    <table>

                        <thead>

                            <tr>
                                <th>ID</th>
                                <th>Amount</th>
                                <th>Category</th>
                                <th>Description</th>
                                <th>Date</th>
                                <th>Payment</th>
                                <th>Action</th>
                            </tr>

                        </thead>

                        <tbody>

                            {expenses.map((expense) => (

                                <tr key={expense.id}>

                                    <td>
                                        {expense.id}
                                    </td>

                                    <td>
                                        ₹{expense.amount}
                                    </td>

                                    <td>
                                        {expense.category}
                                    </td>

                                    <td>
                                        {expense.description}
                                    </td>

                                    <td>
                                        {expense.expenseDate}
                                    </td>

                                    <td>
                                        {expense.paymentMethod}
                                    </td>

                                    <td>

                                        <button
                                            onClick={() =>
                                                handleDelete(expense.id)
                                            }
                                        >
                                            Delete
                                        </button>

                                    </td>

                                </tr>

                            ))}

                        </tbody>

                    </table>

                </div>

            </div>

        </div>
    );
}

export default Expenses;