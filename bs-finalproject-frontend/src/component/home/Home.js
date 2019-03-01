import React from 'react';
import { Link } from 'react-router-dom';

import './Home.scss';
import FeaturedProducts from '../featured-products/FeaturedProducts';

const Home = () => (
  <div className="home">
    <section className="home__hero">
      <div className="home__hero-container">
        <div className="home__hero-content">
          <h1 className="home__hero-heading">Eat Fresh. Be Fresh.</h1>
          <div className="home__hero-description">
            <p className="home__hero-description-paragraph">Order products from Guadalupe's farmers market</p>
            <p className="home__hero-description-paragraph">We'll bring it to you in 24 hours</p>
          </div>
          <Link to="/search" className="home__hero-button">Let's Shop</Link>
        </div>
      </div>
    </section>

    <FeaturedProducts />
  </div>
);

export default Home;
