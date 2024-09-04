import { useEffect } from "react";
import { useState } from "react";
import { removeOfficer, updateOfficer, viewOfficer } from "../utils/fetchAPI";
import Sidebar from "../components/Sidebar";
import { ArrowLeft } from "lucide-react";
import { ArrowRight } from "lucide-react";
import { Edit } from "lucide-react";
import { Trash2 } from "lucide-react";

const ViewOfficer = () => {
  const [officer, setOfficer] = useState([
    {
      officerName: "",
      gender: "",
      officerAge: 0,
      officerAddress: "",
      email: "",
      users: { role: {}}
    },
  ]);
  const [page, setPage] = useState(1);
  const [orderBy, setOrderBy] = useState("officerName");
  const [sortOrder, setSortOrder] = useState("desc");
  const [isLoading, setIsLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [editMode, setEditMode] = useState(false); // Untuk menentukan apakah sedang dalam mode edit
  const [editData, setEditData] = useState({}); // Menyimpan data yang akan diedit

  const handleEditClick = (item) => {
    setEditMode(true);
    setEditData(item);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setEditData({ ...editData, [name]: value });
  };
  

  const handleChangeSort = (e) => {
    let value = e.target.value;

    setSortOrder(value);
  };


  const handlePageRight = () => {
    if (officer.length === 0) {
      setPage(page);
    } else {
      setPage(page + 1);
    }
  };
  const handlePageLeft = () => {
    if (page < 2) {
      setPage(1);
    } else {
      setPage(page - 1);
    }
  };

  const handleChangeOrderBy = (e) => {
    let value = e.target.value;

    setOrderBy(value);
  };

  const fetchOfficer = async () => {
    console.log(orderBy);
    console.log(sortOrder);
    console.log(page);
    console.log(officer);
    setIsLoading(true);
    try {
      const result = await viewOfficer(page, 5, orderBy, sortOrder);
      console.log(result);
      setSuccessMessage("Table Information");
      setOfficer(result);
      setIsLoading(false);
    } catch (error) {
      console.log(error)
      setErrorMessage(error.message);
      setIsLoading(false);
    }
  };
  const handleDelete = async (id) => {
		try {
      window.scrollTo(0,0);
			const result = await removeOfficer(id)
      console.log(result);
			if (result) {
				setSuccessMessage(`Officer was Delete`)
			} else {
				console.error(`Failed deleting officer : ${result.responseMessage}`)
			}
		} catch (error) {
			setErrorMessage(error)
		}
		setTimeout(() => {
			setSuccessMessage("")
			setErrorMessage("")
      window.location.reload();
		}, 3000)
	}

  const handleUpdate = async () => {
    try {
      const result = await updateOfficer(editData.id, editData); // Pastikan updateBook ada di fetchAPI
      console.log(result)
      setSuccessMessage("Officer updated successfully");
      setEditMode(false); // Keluar dari mode edit
      fetchOfficer(); // Refresh data setelah update
    } catch (error) {
      setErrorMessage(error);
    }
    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000);
  };



  useEffect(() => {
    fetchOfficer();
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [orderBy, sortOrder, page]);

  return (
    <>
      <Sidebar />
      <div className="p-8 min-h-screen ml-44 bg-gradient-to-r from-green-400 via-blue-500 to-purple-600 text-white flex justify-center items-center">
        <div className="w-full max-w-3xl bg-white p-8 shadow-lg rounded-lg relative">
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

          <h2 className="text-2xl font-bold text-gray-800 p-6">
          {editMode ? "Edit Officer" : "View Officer"}
          </h2>
          {isLoading ? (
            <div>Loading</div>
          ) : editMode ? (
            <div>
              {/* Form untuk edit */}
              <div className="mb-4">
                <label className="block text-gray-700">Officer Name</label>
                <input
                  type="text"
                  name="name"
                  onChange={handleInputChange}
                  className="w-full px-4 py-2 border rounded-lg text-black"
                  placeholder={editData.officerName}
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700">Officer Age</label>
                <input
                  type="number"
                  name="age"
                  onChange={handleInputChange}
                  className="w-full px-4 py-2 border rounded-lg text-black"
                  placeholder={editData.officerAge}
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700">Gender</label>
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
              <div className="mb-4">
                <label className="block text-gray-700">Address</label>
                <input
                  type="text"
                  name="address"
                  onChange={handleInputChange}
                  className="w-full px-4 py-2 border rounded-lg text-black"
                  placeholder={editData.officerAddress}
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700">Email</label>
                <input
                  type="email"
                  name="email"
                  onChange={handleInputChange}
                  className="w-full px-4 py-2 border rounded-lg text-black"
                  placeholder={editData.email}
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700">Password</label>
                <input
                  type="password"
                  name="password"
                  onChange={handleInputChange}
                  className="w-full px-4 py-2 border rounded-lg text-black"
                  placeholder="Massukan Password"
                />
              </div>

              <button
                onClick={handleUpdate}
                className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded"
              >
                Update
              </button>
            </div>
          ) : (
            <>
              <div className="flex justify-between items-center px-6 pb-4">
                <div className="flex items-center space-x-4">
                  <div>
                    <label className="mr-2 text-gray-600 ">Sort By:</label>
                    <select
                      value={orderBy}
                      className="px-4 py-2 border rounded-lg bg-gray-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      onChange={(e) => {
                        handleChangeOrderBy(e);
                      }}
                    >
                      <option value="officerName">Name</option>
                      <option value="gender">Gender</option>
                      <option value="officerAge">Age</option>
                      <option value="officerAddress">officerAddress</option>
                    </select>
                  </div>
                  <div>
                    <label className="mr-2 text-gray-600">Order:</label>
                    <select
                      value={sortOrder}
                      className="px-4 py-2 border rounded-lg bg-gray-600  focus:outline-none focus:ring-2 focus:ring-blue-500"
                      onChange={(e) => {
                        handleChangeSort(e);
                      }}
                    >
                      <option value="asc">Ascending</option>
                      <option value="desc">Descending</option>
                    </select>
                  </div>
                  <div className="text-black flex justify-between items-center px-6 pb-4 gap-6">
                    <button
                      onClick={() => {
                        handlePageLeft();
                      }}
                    >
                      <ArrowLeft className="bg-gray-600 stroke-green-400" />
                    </button>
                    Page
                    <button
                      onClick={() => {
                        handlePageRight();
                      }}
                    >
                      <ArrowRight className="bg-gray-600 stroke-green-400" />
                    </button>
                  </div>
                </div>
              </div>

              <div className="overflow-x-auto ">
                <table className="min-w-full bg-white">
                  <thead>
                    <tr className="w-full bg-gray-200 text-gray-600 uppercase text-base leading-normal">
                      <th className="py-3 px-1 text-left">officer name</th>
                      <th className="py-3 px-1 text-left">Gender</th>
                      <th className="py-3 px-1 text-left">Age</th>
                      <th className="py-3 px-1 text-left">Address</th>
                      <th className="py-3 px-1 text-left">Email</th>
                      <th className="py-3 px-1 text-left">Users</th>
                      <th className="py-3 px-1 text-left">Actions</th>
                    </tr>
                  </thead>
                  <tbody className="text-gray-600 text-sm font-medium">
                    {officer.map((item, index) => (
                      <tr
                        key={index}
                        className="border-b-2 border-gray-300 hover:bg-slate-300"
                      >
                        <td className="py-3 px-2 text-left whitespace-nowrap">
                          <span className="font-bold">
                            {item.officerName}
                          </span>
                        </td>
                        <td className="py-3 px-1 text-left">{item.gender}</td>
                        <td className="py-3 px-1 text-left">{item.officerAge}</td>
                        <td className="py-3 px-1 text-left">{item.officerAddress}</td>
                        <td className="py-3 px-1 text-left">{item.email}</td>
                        <td className="py-3 px-1 text-left">{item.users.role.roleName}</td>
                        <td className="py-3 px-1 text-left flex gap-2 ">
												<button className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-2 rounded"
                        onClick={()=> handleEditClick(item)}>
													<Edit />
												</button>
											<button
												className="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-2 rounded"
												onClick={()=> handleDelete(item.id)}>
												<Trash2 />
											</button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </>
          )}
        </div>
      </div>
    </>
  );
};

export default ViewOfficer;
