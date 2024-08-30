
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "./AuthProvider"
import {login} from "../utils/fetchAPI"
import { Link } from "react-router-dom";



const Login = () => {
  

  const [errorMessage, setErrorMessage] = useState("")
	const [user, setUser] = useState({
		username: "",
		password: ""
	});

  const navigate = useNavigate();
	const redirectUrl = "/"
	const auth = useAuth();

  const handleInputChange = (e) => {
		setUser({ ...user, [e.target.name]: e.target.value })
	}

  const handleSubmit = async (e) => {
		e.preventDefault()
		const success = await login(user)
    console.log(success)
		if (success) {
			const token = success.token
			auth.handleLogin(token)
      console.log(auth.handleLogin(token))
			navigate(redirectUrl, { replace: true })
			window.location.reload()
		} else {
			setErrorMessage("Invalid username or password. Please try again.")
		}
		setTimeout(() => {
			setErrorMessage("")
		}, 4000)
	}



  return (
    <div className="min-h-screen flex items-center justify-center bg-slate-300">
      <div className="bg-white p-8 rounded-lg shadow-lg max-w-md w-full">
        <h2 className="text-2xl font-bold text-center mb-6 text-gray-800">Login to Your Account</h2>
        {errorMessage && (
          <div className="absolute top-0 left-0 w-full bg-red-600 text-white text-center pt-10 pb-3 rounded-lg shadow-lg transform -translate-y-8">
            <span className="inline-block bg-red-800 px-4 py-2 rounded-full">
              {errorMessage}
            </span>
          </div>
        )}
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label className="block text-gray-700 font-semibold mb-2" htmlFor="email">
              Username
            </label>
            <input
              type="email"
              id="email"
              name="username"
              value={user.username}
							onChange={handleInputChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Enter your email"
            />
          </div>
          <div className="mb-6">
            <label className="block text-gray-700 font-semibold mb-2" htmlFor="password">
              Password
            </label>
            <input
              type="password"
              name="password"
              id="password"
              value={user.password}
							onChange={handleInputChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Enter your password"
            />
          </div>
          <div className="flex items-center justify-between mb-6">
            <label className="inline-flex items-center">
              <input type="checkbox" className="form-checkbox text-blue-600" />
              <span className="ml-2 text-gray-700">Remember me</span>
            </label>
            <Link to="/forgot" className="text-blue-500 hover:underline">Forgot password?</Link>
          </div>
          <button
            type="submit"
            className="w-full bg-blue-500 text-white font-semibold py-2 px-4 rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
          >
            Login
          </button>
        </form>
        <p className="mt-6 text-center text-gray-700">
          Dont have an account? <Link to="/regis" className="text-blue-500 hover:underline">Sign up</Link>
        </p>
      </div>
    </div>
  );
};

export default Login;
