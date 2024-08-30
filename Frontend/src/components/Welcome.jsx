
const Welcome = () => {
  return (
    <div className="relative h-screen w-full flex items-center justify-center text-white">
      {/* Background Image */}
      <div
        className="absolute inset-0 bg-cover bg-center z-0"
        style={{
          backgroundImage: "url('src/image/—Pngtree—an old bookcase in a_2760144.jpg')",
        }}
      ></div>

      {/* Overlay */}
      <div className="absolute inset-0 bg-black opacity-50 z-0"></div>

      {/* Welcome Text */}
      <div className="relative z-10 text-center px-4">
        <h1 className="text-6xl font-bold mb-6">
          Welcome to <span className="text-yellow-400">BookSpace</span>
        </h1>
        <p className="text-lg max-w-xl mx-auto">
          BookSpace is your ultimate library management system. Easily manage your book collection, track member activities, and maintain a smooth workflow. Whether {"you're"} an admin or a user, BookSpace is designed to make library management a breeze.
        </p>
      </div>
    </div>
  );
};

export default Welcome;
