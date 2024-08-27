import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { forgotPassword, resetPassword } from "../utils/fetchAPI";

const ForgotPassword = () => {
  const [email, setEmail] = useState("");
  const [token, setToken] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const navigate = useNavigate();
  const redirectUrl = "/login";

  const handleEmail = async (e) => {
    e.preventDefault();
    const success = await forgotPassword(email[""]);
    console.log(email[""]);
    console.log(success);
    if (success) {
      console.log("Sukses");
      setSuccessMessage("The token has been sent to your email.");
    } else {
      setErrorMessage("the email was not found in the database.");
    }
    setTimeout(() => {
      setErrorMessage("");
      setSuccessMessage("");
    }, 2000);
  };

  const handleInputEmail = (e) => {
    setEmail({ ...email, [e.target.name]: e.target.value });
  };
  const handleInputToken = (e) => {
    setToken({ ...token, [e.target.name]: e.target.value });
  };
  const handleInputNewPass = (e) => {
    setNewPassword({ ...newPassword, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const success = await resetPassword(token[""], newPassword[""]);
    console.log(token[""]);
    console.log(newPassword[""]);
    console.log(success);
    if (success && newPassword.length >= 8) {
      
      navigate(redirectUrl, { replace: true });
      window.location.reload();
    } else if (newPassword.length < 8) {
      setErrorMessage("your new password is less than 8 characters");
    } else {
      setErrorMessage("The token you entered has expired");
    }
    setTimeout(() => {
      setErrorMessage("");
    }, 2000);
  };



  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="bg-white p-8 rounded-lg shadow-lg max-w-md w-full relative">
        <h2 className="text-2xl font-bold text-center mb-6 text-gray-800">
          Forgot Password
        </h2>

        {/* Menampilkan pesan sukses atau kesalahan jika ada */}
        {successMessage && (
          <div className="absolute top-0 left-0 w-full bg-green-500 text-white text-center py-4 rounded-lg shadow-lg transform -translate-y-8">
            <span className="inline-block bg-green-700 px-4 py-2 rounded-full">
              {successMessage}
            </span>
          </div>
        )}

        {errorMessage && (
          <div className="absolute top-0 left-0 w-full bg-red-500 text-white text-center py-4 rounded-lg shadow-lg transform -translate-y-8">
            <span className="inline-block bg-red-700 px-4 py-2 rounded-full">
              {errorMessage}
            </span>
          </div>
        )}

        <form onSubmit={handleEmail} className="space-y-4">
          <div>
            <label
              className="block text-gray-700 font-semibold mb-2"
              htmlFor="email"
            >
              Email Address
            </label>
            <input
              type="email"
              id="email"
              value={email.email}
              onChange={handleInputEmail}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Enter your email"
            />
          </div>
          <button
            type="submit"
            className="w-full bg-blue-500 text-white font-semibold py-2 px-4 rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
          >
            Ceck Email
          </button>
        </form>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label
              className="block text-gray-700 font-semibold mb-2"
              htmlFor="token"
            >
              Reset Token
            </label>
            <input
              type="text"
              id="token"
              value={token.token}
              onChange={handleInputToken}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Enter your reset token"
            />
          </div>
          <div>
            <label
              className="block text-gray-700 font-semibold mb-2"
              htmlFor="new-password"
            >
              New Password
            </label>
            <input
              type="password"
              id="new-password"
              value={newPassword.newPassword}
              onChange={handleInputNewPass}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Enter your new password"
            />
          </div>
          <button
            type="submit"
            className="w-full bg-blue-500 text-white font-semibold py-2 px-4 rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
          >
            Reset Password
          </button>
        </form>
      </div>
    </div>
  );
};

export default ForgotPassword;
