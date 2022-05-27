import { Outlet, Link } from "react-router-dom";
import '../WebContent/css/General.css';
import '../WebContent/css/Layout.css';

const Layout = () => {
  return (
    <>
      <h1 id="header" class="titre">MemeGenerator </h1>
      <nav>
        <ul>
          <li>
            <Link to="/home" class="url">Home</Link>
          </li>
          <li>
            <Link to="/" class="url">Generate</Link>
          </li>
          <li>
            <Link to="/login" class="url">Login</Link>
          </li>
          <li>
            <Link to="/about" class="url">About</Link>
          </li>
          <li>
            <Link to="/contact" class="lasturl">Contact</Link>
          </li>
        </ul>
      </nav>

      <Outlet />
    </>
  )
};

export default Layout;
