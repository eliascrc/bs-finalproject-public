import React from 'react';
import { withRouter } from 'react-router-dom';

import './Footer.scss';

const Footer = ({ location }) => {
  const hideFooter = location.pathname.includes('login')  || location.pathname.includes("join");
  return (
    <div>
      {
        hideFooter ? null :
          <footer className="footer">
            <div className="footer__github">See this projects source code at <a className="footer__anchor" href="https://github.com/eliascrc/bs-finalproject-app" target="blank"><i className="fab fa-github footer__icon"></i> Github</a></div>
            <div className="footer__disclaimer">This project was created by Elías Calderón for academical purposes only</div>
          </footer>
      }
    </div>
  )
}

export default withRouter(Footer);
