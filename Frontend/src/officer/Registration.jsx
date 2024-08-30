import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { registerMember } from "../utils/fetchAPI";

const Registration = () => {
  const [registration, setRegistration] = useState({
    name: "",
    age: "",
    gender: "",
    Addres: "",
    photo: "",
    email: "",
    password: "",
  });

  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const navigate = useNavigate();
  const redirectUrl = "/home";

  const handleInputChange = (e) => {
    setRegistration({ ...registration, [e.target.name]: e.target.value });
  };

  const handleRegistration = async (e) => {
    e.preventDefault();
    try {
      const result = await registerMember(registration);
      console.log(result);
      setSuccessMessage("Sukses");
      setErrorMessage("");
      setRegistration({
        name: "",
        age: "",
        gender: "",
        Addres: "",
        photo: "",
        email: "",
        password: "",
      });
    } catch (error) {
      setSuccessMessage("");
      setErrorMessage(`Registration error : ${error.message}`);
    }
    setTimeout(() => {
      setErrorMessage("");
      setSuccessMessage("");
      navigate(redirectUrl, { replace: true });
    }, 5000);
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="bg-white p-8 rounded-lg shadow-lg max-w-md w-full relative">
        <h2 className="text-2xl font-bold text-center mb-6 text-gray-800">
          Registration
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

        <form onSubmit={(e)=>{handleRegistration(e)}} className="space-y-4">
          <div>
            <label
              className="block text-gray-700 font-semibold mb-2"
              htmlFor="name"
            >
              Name
            </label>
            <input
              type="text"
              id="name"
              value={registration.name}
              onChange={(e)=>{handleInputChange(e)}}
              name="name"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Enter your name"
            />
          </div>

          <div>
            <label
              className="block text-gray-700 font-semibold mb-2"
              htmlFor="age"
            >
              Age
            </label>
            <input
              type="number"
              id="age"
              value={registration.age}
              onChange={(e)=>{handleInputChange(e)}}
              name="age"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Enter your age"
            />
          </div>

          <div>
            <label
              className="block text-gray-700 font-semibold mb-2"
              htmlFor="gender"
            >
              Gender
            </label>
            <select
              id="gender"
              value={registration.gender}
              onChange={(e)=>{handleInputChange(e)}}
              name="gender"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="">Select your gender</option>
              <option value="male">Male</option>
              <option value="female">Female</option>
            </select>
          </div>

          <div>
            <label
              className="block text-gray-700 font-semibold mb-2"
              htmlFor="Addres"
            >
              Addres
            </label>
            <input
              type="text"
              id="Addres"
              value={registration.Addres}
              onChange={(e)=>{handleInputChange(e)}}
              name="Addres"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Enter your Addres"
            />
          </div>

          <div>
            <label
              className="block text-gray-700 font-semibold mb-2"
              htmlFor="photo"
            >
              Photo URL
            </label>
            <input
              type="text"
              id="photo"
              value={registration.photo}
              onChange={(e)=>{handleInputChange(e)}}
              name="photo"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Enter your photo URL"
            />
          </div>

          <div>
            <label
              className="block text-gray-700 font-semibold mb-2"
              htmlFor="email"
            >
              Email
            </label>
            <input
              type="email"
              id="email"
              value={registration.email}
              onChange={(e)=>{handleInputChange(e)}}
              name="email"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Enter your email"
            />
          </div>

          <div>
            <label
              className="block text-gray-700 font-semibold mb-2"
              htmlFor="password"
            >
              Password
            </label>
            <input
              type="password"
              id="password"
              value={registration.password}
              onChange={(e)=>{handleInputChange(e)}}
              name="password"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Enter your password"
            />
          </div>
          <button
            type="submit"
            className="w-full bg-blue-500 text-white font-semibold py-2 px-4 rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
          >
            Registration
          </button>
        </form>
      </div>
    </div>
  );
};

export default Registration;
