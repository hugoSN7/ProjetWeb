import { Outlet, Link } from "react-router-dom";
import '../WebContent/css/General.css';
import '../WebContent/css/Layout.css';

const Layout = () => {
  return (
    <>
      <h1 class="titre">MemeGenerator </h1>
      <nav>
        <ul>
          <li>
            <Link to="/home" class="urlHome">Home</Link>
          </li>
          <li>
            <Link to="/" class="urlGenerate">Generate</Link>
          </li>
          <li>
            <Link to="/login" class="urlLogin">Login</Link>
          </li>
          <li>
            <Link to="/about" class="urlAbout">About</Link>
          </li>
          <li>
            <Link to="/contact" class="urlContact">Contact</Link>
          </li>
        </ul>
      </nav>

      <Outlet />
    </>
  )
};

export default Layout;
