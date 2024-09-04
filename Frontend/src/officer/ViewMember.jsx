import { useEffect } from "react";
import { useState } from "react";
import { removeMember, viewMember, updateMember } from "../utils/fetchAPI";
import Sidebar from "../components/Sidebar";
import { ArrowLeft } from "lucide-react";
import { ArrowRight } from "lucide-react";
import { Edit } from "lucide-react";
import { Trash2 } from "lucide-react";




const ViewMember = () => {
  const [member, setMember] = useState([
    {
      memberName: "",
      gender: "",
      memberAge: 0,
      address: "",
      email: "",
      user: { role: {}}
    },
  ]);
  const [page, setPage] = useState(1);
  const [orderBy, setOrderBy] = useState("memberName");
  const [sortOrder, setSortOrder] = useState("desc");
  const [isLoading, setIsLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [editMode, setEditMode] = useState(false); // Untuk menentukan apakah sedang dalam mode edit
  const [editData, setEditData] = useState({}); // Menyimpan data yang akan diedit


  const handleChangeSort = (e) => {
    let value = e.target.value;

    setSortOrder(value);
  };

  const handlePageRight = () => {
    if (member.length === 0) {
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

  const fetchMember = async () => {
    console.log(orderBy);
    console.log(sortOrder);
    console.log(page);
    console.log(member);
    setIsLoading(true);
    try {
      const result = await viewMember(page, 5, orderBy, sortOrder);
      console.log(result);
      setSuccessMessage("Table Information");
      setMember(result);
      setIsLoading(false);
    } catch (error) {
      setErrorMessage(error.message);
      setIsLoading(false);
    }
  };

  const handleEditClick = (item) => {
    setEditMode(true);
    setEditData(item); // Set data yang akan di-edit
  };

  const handleDelete = async (id) => {
		try {
      window.scrollTo(0,0);
			const result = await removeMember(id)
      console.log(result);
			if (result) {
				setSuccessMessage(`Member was Delete`)
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

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setEditData({ ...editData, [name]: value }); // Update nilai dari field yang diubah
  };


  const handleUpdate = async () => {
    try {
      const result = await updateMember(editData.id, editData); // Pastikan updateBook ada di fetchAPI
      console.log(result)
      setSuccessMessage("Member updated successfully");
      setEditMode(false); // Keluar dari mode edit
      fetchMember(); // Refresh data setelah update
    } catch (error) {
      setErrorMessage(error);
    }
    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000);
  };




  useEffect(() => {
    fetchMember();
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
          {editMode ? "Edit Member" : "View Member"}
          </h2>
          {isLoading ? (
            <div>Loading</div>
          ) : editMode ? (
            <div>
              {/* Form untuk edit */}
              <div className="mb-4">
                <label className="block text-gray-700">Member Name</label>
                <input
                  type="text"
                  name="name"
                  onChange={handleInputChange}
                  className="w-full px-4 py-2 border rounded-lg text-black"
                  placeholder={editData.memberName}
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700">Member Age</label>
                <input
                  type="number"
                  name="age"
                  onChange={handleInputChange}
                  className="w-full px-4 py-2 border rounded-lg text-black"
                  placeholder={editData.memberAge}
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
                  placeholder={editData.address}
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
                  placeholder="Masukan Password"
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
                      <option value="memberName">Name</option>
                      <option value="gender">Gender</option>
                      <option value="memberAge">Age</option>
                      <option value="address">Address</option>
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
                      <th className="py-3 px-1 text-left">Member name</th>
                      <th className="py-3 px-1 text-left">Gender</th>
                      <th className="py-3 px-1 text-left">Age</th>
                      <th className="py-3 px-1 text-left">Address</th>
                      <th className="py-3 px-1 text-left">Email</th>
                      <th className="py-3 px-1 text-left">User</th>
                      <th className="py-3 px-1 text-left">Actions</th>
                    </tr>
                  </thead>
                  <tbody className="text-gray-600 text-sm font-medium">
                    {member.map((item, index) => (
                      <tr
                        key={index}
                        className="border-b-2 border-gray-300 hover:bg-slate-300"
                      >
                        <td className="py-3 px-2 text-left whitespace-nowrap">
                          <span className="font-bold">
                            {item.memberName}
                          </span>
                        </td>
                        <td className="py-3 px-1 text-left">{item.gender}</td>
                        <td className="py-3 px-1 text-left">{item.memberAge}</td>
                        <td className="py-3 px-1 text-left">{item.address}</td>
                        <td className="py-3 px-1 text-left">{item.email}</td>
                        <td className="py-3 px-1 text-left">{item.user.role.roleName}</td>
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

export default ViewMember;
