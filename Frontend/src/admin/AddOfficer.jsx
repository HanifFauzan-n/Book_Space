import { useState } from "react";
import Sidebar from "../components/Sidebar.jsx";
import { registerOfficer } from "../utils/fetchAPI.js";
import { useNavigate } from "react-router-dom";

const AddOfficer = () => {
  const [registration, setRegistration] = useState({
    name: "",
    age: "",
    gender: "",
    address: "",
    photo: "",
    email: "",
    password: "",
  });
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const handleInputChange = (e) => {
    setRegistration({ ...registration, [e.target.name]: e.target.value });
  };

  const navigate = useNavigate();
  const redirectUrl = "/officer"


  const handleSubmit = async (e) => {
    e.preventDefault();
    window.scrollTo(0,0);

    try {
      const res = await registerOfficer(registration);
      console.log(res);
      setSuccessMessage("A new officer was added successfully !");
      if (res.success) {
        setRegistration({
          name: "",
          age: "",
          gender: "",
          address: "",
          photo: "",
          email: "",
          password: "",
        });
        navigate(redirectUrl, { replace: true })
        // window.location.reload()
        setErrorMessage("");
      } else {
        setErrorMessage("Error adding new officer");
      }
    } catch (error) {
      console.log(error);
      if(error === "ERR_BAD_REQUEST"){
        setErrorMessage("Access Denied")
      }
      else
      setErrorMessage(error)
    }
    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000);
  };

  return (
    <>
      <Sidebar />
      <div className="p-8 min-h-screen bg-gradient-to-r ml-44 from-green-400 via-blue-500 to-purple-600 text-white flex justify-center items-center">
        <div className="w-full max-w-lg bg-white p-8 shadow-lg rounded-lg relative">
          {/* Pop-up Success Message */}
          {successMessage && (
            <div className="absolute top-0 left-0 w-full bg-green-500 text-white text-center py-3 rounded-t-lg shadow-lg">
              {successMessage}
            </div>
          )}

          {/* Pop-up Error Message */}
          {errorMessage && (
            <div className="absolute top-0 left-0 w-full bg-red-500 text-white text-center py-3 rounded-t-lg shadow-lg">
              {errorMessage}
            </div>
          )}
          <h2 className="text-2xl font-bold mb-6 mt-5 text-gray-800 text-center">
            Create New Officer
          </h2>

          <form onSubmit={handleSubmit}>
            {/* Name Field */}
            <div className="mb-4">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="name"
              >
                Name
              </label>
              <input
                type="text"
                id="name"
                name="name"
                onChange={(e) => {
                  handleInputChange(e);
                }}
                className="w-full px-3 py-2 border rounded-lg text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Enter Name"
              />
            </div>

            {/* Age Field */}
            <div className="mb-4">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="age"
              >
                Age
              </label>
              <input
                type="number"
                id="age"
                name="age"
                onChange={(e) => {
                  handleInputChange(e);
                }}
                className="w-full px-3 py-2 border rounded-lg text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Enter Age"
              />
            </div>

            {/* Gender Field */}
            <div className="mb-4">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="gender"
              >
                Gender
              </label>
              <select
                id="gender"
                name="gender"
                onChange={(e) => {
                  handleInputChange(e);
                }}
                className="w-full px-3 py-2 border rounded-lg text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
              >
                <option value="">Select Gender</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
              </select>
            </div>

            {/* Address Field */}
            <div className="mb-4">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="address"
              >
                address
              </label>
              <input
                type="text"
                id="address"
                name="address"
                onChange={(e) => {
                  handleInputChange(e);
                }}
                className="w-full px-3 py-2 border rounded-lg text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Enter address"
              />
            </div>
            {/* Email Field */}
            <div className="mb-4">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="email"
              >
                Email
              </label>
              <input
                type="email"
                id="email"
                name="email"
                onChange={(e) => {
                  handleInputChange(e);
                }}
                className="w-full px-3 py-2 border rounded-lg text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Enter Email"
              />
            </div>

            {/* Password Field */}
            <div className="mb-6">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="password"
              >
                Password
              </label>
              <input
                type="password"
                id="password"
                name="password"
                onChange={(e) => {
                  handleInputChange(e);
                }}
                className="w-full px-3 py-2 border rounded-lg text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Enter Password"
              />
            </div>

            {/* Submit Button */}
            <div className="flex justify-center">
              <button
                type="submit"
                className="w-full bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              >
                Create
              </button>
            </div>
          </form>
        </div>
      </div>
    </>
  );
};

export default AddOfficer;
