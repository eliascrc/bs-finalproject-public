import React from 'react';
import { withRouter } from 'react-router-dom';

import './ProductOverview.scss';
import { capitalizeName } from '../../utils/commons';

const ProductOverview = ({ product, history }) => {
  return (
    <button onClick={(e) => {e.preventDefault(); history.push(`/product/${product.id}`)}} className="product-overview">
      <div className="product-overview__image-wrapper">
        <img className="product-overview__image" src={product.imageUrl} alt={`Product card for ${product.name}`} />
      </div>
      <div className="product-overview__caption">
        <div className="product-overview__name">
          {capitalizeName(product.name)}
        </div>
        <div className="product-overview__price">
          ${product.price}
        </div>
        <div className="product-overview__stars">
          {Array.apply(null, { length: product.averageRating }).map((el, i) =>
            <i key={i} className="fas fa-star product-overview__star"></i>
          )}
          {Array.apply(null, { length: (5 - product.averageRating) }).map((el, i) =>
            <i key={i} className="far fa-star product-overview__star"></i>
          )}
        </div>
      </div>
    </button>
  )
}

export default withRouter(ProductOverview);