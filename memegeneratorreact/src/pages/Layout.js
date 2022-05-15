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
            <p class="trait1">|</p>
          </li>
          <li>
            <Link to="/" class="urlGenerate">Generate</Link>
            <p class="trait2">|</p>
          </li>
          <li>
            <Link to="/login" class="urlLogin">Login</Link>
            <p class="trait3">|</p>
          </li>
          <li>
            <Link to="/about" class="urlAbout">About</Link>
            <p class="trait4">|</p>
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
