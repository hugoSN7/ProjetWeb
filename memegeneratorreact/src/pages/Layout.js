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
            <div class="trait1"></div>
          </li>
          <li>
            <Link to="/" class="urlGenerate">Generate</Link>
            <div class="trait2"></div>
          </li>
          <li>
            <Link to="/login" class="urlLogin">Login</Link>
            <div class="trait3"></div>
          </li>
          <li>
            <Link to="/about" class="urlAbout">About</Link>
            <div class="trait4"></div>
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
