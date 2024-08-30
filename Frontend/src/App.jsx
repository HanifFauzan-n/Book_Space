import { Outlet } from "react-router-dom";
import AuthProvider from "./auth/AuthProvider";
import { ContentProvider } from "./components/ContentProvider";

function App() {
  return (
    <>
      <AuthProvider>
        <ContentProvider>
          <Outlet />
        </ContentProvider>
      </AuthProvider>
    </>
  );
}

export default App;
