import axios from "axios";

const API_URL = "https://expensesportalbackend.onrender.com";

export const registerUser = async (registerData) => {
    const response = await axios.post(
        `${API_URL}/auth/register`,
        registerData
    );
    
    return response.data;
};

export const loginUser = async (loginData) => {
    const response = await axios.post(
        `${API_URL}/auth/login`,
        loginData
    );
console.log(response.data)
    return response.data;
};

export const logoutUser = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    localStorage.removeItem("role");
};