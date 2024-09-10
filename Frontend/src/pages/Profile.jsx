import { useState } from "react";
import {
  viewBook,
  viewBookshelf,
  viewMember,
  viewOfficer,
} from "../utils/fetchAPI";
import { useEffect } from "react";
import { Link } from "react-router-dom";

const Profile = () => {
  const [member, setMember] = useState();
  const [officer, setOfficer] = useState();
  const [book, setBook] = useState();
  const [bookshelf, setBookshelf] = useState();

  const fetchView = async () => {
    try {
      const resultBook = await viewBook(1, 50, "id", "asc");
      setBook(resultBook.length);

      const resultBookshelf = await viewBookshelf(1, 50, "id", "asc");
      setBookshelf(resultBookshelf.length);

      const resultMember = await viewMember(1, 50, "id", "asc");
      setMember(resultMember.length);

      const resultOfficer = await viewOfficer(1, 50, "id", "asc");
      setOfficer(resultOfficer.length);
    } catch (error) {
      console.log(error);
    }
  };
  useEffect(() => {
    fetchView();
  }, []);

  return (
    <div className="flex items-center justify-center min-h-screen bg-gradient-to-r from-green-400 via-blue-500 to-violet-500">
      <div className="bg-white shadow-2xl rounded-3xl p-8 max-w-lg w-full">
        <Link to={"/"} className="bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded-full">
          Home
        </Link>
        <div className="flex flex-col items-center">
          <div className="relative">
            <img
              src="src/image/Profile-Admin.jpg"
              alt="Profile"
              className="w-36 h-36 rounded-full shadow-xl border-4 border-black "
            />
            <span className="absolute bottom-0 right-0 w-8 h-8 bg-green-400 border-2 border-white rounded-full"></span>
          </div>
          <h2 className="mt-6 text-3xl font-bold text-gray-900">
            Profile Admin
          </h2>
          <p className="text-xl text-gray-700">Hanif Fauzan Nurrahman</p>
          <div className="mt-4 flex space-x-3">
            <a

              className="bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2 px-6 rounded-full shadow-md"
            >
              Follow
            </a>
            <a

              className="bg-pink-500 hover:bg-pink-600 text-white font-semibold py-2 px-6 rounded-full shadow-md"
            >
              Message
            </a>
          </div>
        </div>
        <div className="mt-8">
          <h3 className="text-gray-900 text-xl font-semibold">About</h3>
          <p className="mt-3 text-gray-700 text-sm leading-relaxed">
            BookSpace adalah platform perpustakaan digital yang memudahkan petugas melakukan
            pencatatan, pengelolaan, dan pencarian buku. Kami hadir untuk
            memberikan akses cepat dan efisien ke koleksi buku, serta
            mempermudah proses peminjaman dan pengembalian dengan sistem yang
            terintegrasi.{" "}
          </p>
        </div>
        <div className="mt-5 flex">
          <h3 className="text-gray-700 text-sm font-semibold mr-1">Email : </h3>
          <p className=" text-gray-700 text-sm leading-relaxed">
            haniffauzann@gmail.com
          </p>
        </div>
        <div className="mt-1 flex">
          <h3 className="text-gray-700 text-sm font-semibold mr-1">
            Address :{" "}
          </h3>
          <p className=" text-gray-700 text-sm leading-relaxed">
            Cilacap, Jawa Tengah
          </p>
        </div>
        <div className="mt-1 flex">
          <h3 className="text-gray-700 text-sm font-semibold mr-1">
            Website created on :{" "}
          </h3>
          <p className=" text-gray-700 text-sm leading-relaxed">
            04 - 09 - 2024
          </p>
        </div>
        <div className="mt-1 flex">
          <h3 className="text-gray-700 text-sm font-semibold mr-1">
            Website Version :{" "}
          </h3>
          <p className=" text-gray-700 text-sm leading-relaxed">
            2.0 {("(Beta)")}
          </p>
        </div>
        <h3 className="text-gray-900 text-xl pt-5 mb-0 pb-0 font-semibold">
          Existing items
        </h3>
        <div className="mt-6 grid grid-cols-2 gap-4">
          <div className="bg-purple-100 p-4 rounded-lg text-center shadow-md">
            <h4 className="text-gray-900 font-semibold">
              {book ? "Books" : "Book"}
            </h4>
            <p className="text-purple-700 font-bold text-xl">{book} book</p>
          </div>
          <div className="bg-pink-100 p-4 rounded-lg text-center shadow-md">
            <h4 className="text-gray-900 font-semibold">
              {bookshelf ? "Bookshelf's" : "Bookshelf"}
            </h4>
            <p className="text-pink-700 font-bold text-xl">{bookshelf} item</p>
          </div>
          <div className="bg-blue-100 p-4 rounded-lg text-center shadow-md">
            <h4 className="text-gray-900 font-semibold">
              {officer ? "Officers" : "Officer"}
            </h4>
            <p className="text-blue-700 font-bold text-xl">{officer} officer</p>
          </div>
          <div className="bg-green-100 p-4 rounded-lg text-center shadow-md">
            <h4 className="text-gray-900 font-semibold">
              {member ? "Members" : "Member"}
            </h4>
            <p className="text-green-700 font-bold text-xl">{member} member</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Profile;
