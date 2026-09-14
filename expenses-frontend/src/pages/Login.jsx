import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { loginUser } from "../components/authService";

function Login() {

    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        usernameOrEmail: "",
        password: ""
    });

    const [error, setError] = useState("");

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

            const response = await loginUser(formData);

            console.log("Login response:", response);

            /*
             Expected backend response:

             {
                "token": "...",
                "username": "dileep",
                "role": "ROLE_USER"
             }
            */

            localStorage.setItem("token",response.token);
            localStorage.setItem("username",response.username);
            localStorage.setItem("role",response.role);
            navigate("/expenses");

        } catch (err) {

            console.error(err);

            if (err.response?.data?.message) {
                setError(err.response.data.message);
            } else {
                setError("Invalid username/email or password");
            }
        }
    };

    return (
        <div className="auth-container">

            <div className="auth-card">

                <h1>Expenses Portal</h1>

                <h2>Login</h2>

                <form onSubmit={handleSubmit}>

                    <input
                        type="text"
                        name="usernameOrEmail"
                        placeholder="Username or Email"
                        value={formData.usernameOrEmail}
                        onChange={handleChange}
                        required
                    />

                    <input
                        type="password"
                        name="password"
                        placeholder="Password"
                        value={formData.password}
                        onChange={handleChange}
                        required
                    />

                    <button type="submit">
                        Login
                    </button>

                </form>

                {error && (
                    <p className="error">
                        {error}
                    </p>
                )}

                <p>
                    Don't have an account?
                    <Link to="/register"> Register</Link>
                </p>

            </div>

        </div>
    );
}

export default Login;