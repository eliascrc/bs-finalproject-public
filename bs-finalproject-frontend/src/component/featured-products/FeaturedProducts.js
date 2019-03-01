import React, { Component } from 'react';
import Slider from 'react-slick';

import './FeaturedProducts.scss';
import { getFeaturedProducts } from '../../services/ProductService';
import ProductOverview from '../product-overview/ProductOverview';

class FeaturedProducts extends Component {

  constructor(props) {
    super(props);

    this.state = {
      featuredProductsList: [],
    }
  }

  settings = {
    slidesToShow: 4,
    responsive: [
      {
        breakpoint: 1024,
        settings: {
          slidesToShow: 3,
        }
      },
      {
        breakpoint: 768,
        settings: {
          slidesToShow: 2,
        }
      },
      {
        breakpoint: 580,
        settings: {
          slidesToShow: 1,
        }
      },
    ]
  };

  handleLeftCarousel = () => {
    document.getElementsByClassName('slick-prev').item(0).click();
  }

  handleRightCarousel = () => {
    document.getElementsByClassName('slick-next').item(0).click();
  }

  mapFeaturedProducts = (products) => {
    return products.map((product, i) => (
      <ProductOverview key={i} product={product} />
    ));
  }

  componentDidMount() {
    getFeaturedProducts()
      .then( res => {
        return res.json()
      }).then( res => {
        const featuredProductsList = this.mapFeaturedProducts(res.response);
        this.setState({ featuredProductsList });
      })
      .catch( err => {
        console.error('Unexpected error while fetching the featured products');
        console.error(err);
      });
    
  }

  render() {
    const { featuredProductsList } = this.state;
    return (
      <section className="featured-products">
        <div className="featured-products__container">
          <h1 className="featured-products__heading">
            Featured Products
          </h1>

          <p className="featured-products__description">
            Here you can find some of our best products which are selling like crazy lately!
          </p>

          <div className="featured-products__carousel">
            <button onClick={this.handleLeftCarousel} className="featured-products__carousel-left"><i className="fas fa-chevron-left"></i></button>
            <div className="featured-products__carousel-wrapper">
              <Slider {...this.settings}>
                {featuredProductsList}
              </Slider>
            </div>
            <button onClick={this.handleRightCarousel} className="featured-products__carousel-right"><i className="fas fa-chevron-right"></i></button>
          </div>
        </div>
      </section>
    );
  }
}

export default FeaturedProducts;
