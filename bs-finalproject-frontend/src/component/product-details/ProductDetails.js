import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import { connect } from 'react-redux';

import './ProductDetails.scss';
import { getProductById, getMyProductRating, setProductRating } from '../../services/ProductService';
import { capitalizeName } from '../../utils/commons';
import { addToBag, removeFromBag, clearBag } from '../../redux/actions/ShoppingBagActions';
import { logout } from '../../redux/actions/UserActions';
import { Auth } from 'aws-amplify';
import LoadingScreen from '../loading-screen/LoadingScreen';

class ProductDetails extends Component {

  constructor(props) {
    super(props);

    this.state = {
      product: null,
      amountSelected: 1,
      name: '',
      imageUrl: '',
      price: '',
      ranking: 0,
      amount: '',
      inStock: 0,
      description: '',
      myRating: [false, false, false, false, false],
      myRatingView: [false, false, false, false, false],
      ratingMessage: '',
      loading: false,
    }
  }

  handleChangeAmount = (e, amount) => {
    e.preventDefault();
    const { amountSelected, inStock } = this.state;
    const newAmount = amountSelected + amount;

    if (newAmount > 0 & newAmount <= inStock) {
      this.setState({ amountSelected: newAmount });
    }
  }

  handleAmountOnBlur = (e) => {
    e.preventDefault();
    const { inStock, amountSelected } = this.state;
    let newAmount = amountSelected;

    if (amountSelected === '' || amountSelected < 1) {
      newAmount = 1;
    } else if (amountSelected > inStock) {
      newAmount = inStock
    }

    this.setState({ amountSelected: newAmount });
  }

  handleAmountOnChange = (e) => {
    e.preventDefault();
    let newAmount = e.target.value;

    if (!isNaN(newAmount)) {
      this.setState({ amountSelected: newAmount });
    }

  }

  addToShoppingBag = (e) => {
    e.preventDefault();
    const { onAddToBag } = this.props;
    const { product, amountSelected } = this.state;
    product.amountSelected = parseInt(amountSelected);
    onAddToBag(product);
  }

  removeFromShoppingBag = (e) => {
    e.preventDefault();
    const { onRemoveFromBag } = this.props;
    const { product } = this.state;
    onRemoveFromBag(product);
  }

  isProductInShoppingBag = () => {
    const { product } = this.state;

    if (product !== null) {
      const { shoppingBag } = this.props;
      for (let index = 0; index < shoppingBag.length; index++) {
        const productInBag = shoppingBag[index];
        if (product.id === productInBag.id) {
          return true;
        }
      }
    }

    return false;
  }

  loadProduct = () => {
    const { match, history } = this.props;
    this.setState({ loading: true });
    getProductById(match.params.id)
      .then(res => {
        if (!res.ok) { throw res; }
        return res.json();

      }).then(res => {
        this.setState({
          product: res.response,
          name: res.response.name,
          imageUrl: res.response.imageUrl,
          price: res.response.price,
          ranking: res.response.averageRating,
          amount: res.response.amount,
          inStock: res.response.inStock,
          description: res.response.description,
          loading: false,
        })
      }).catch(err => {
        if (err.status === 404 || err.status === 400) {
          history.push('/');
        } else {
          console.error('Unexpected error');
          console.error(err);
        }
      })
  }

  loadUserRating = () => {
    const { match, history, loggedUser, onLogout } = this.props;
    getMyProductRating(match.params.id, loggedUser)
      .then(res => {
        if (!res.ok) { throw res; }
        return res.json();

      }).then(res => {
        const myRating = [false, false, false, false, false];
        for (let index = 0; index < res.response.givenScore; index++) {
          myRating[index] = true;
        }

        this.setState({
          myRating: myRating,
          myRatingView: myRating,
        })
      }).catch(err => {
        if (err.status === 404) {
          history.push('/login');
        } else if (err.status === 401) {
          Auth.signOut()
            .then(data => onLogout())
            .catch(err => {
              console.error('Unexpected error while loggin out');
              console.error(err);
            });
        } else {
          console.error('Unexpected error');
          console.error(err);
        }
      })
  }

  handleStarSelect(e, selectedStar) {
    e.preventDefault();

    const myRating = [false, false, false, false, false];
    for (let index = 0; index < selectedStar + 1; index++) {
      myRating[index] = true;
    }
    this.setState({
      myRatingView: myRating,
    })
  }

  handleStarClick(e, selectedStar) {
    e.preventDefault();

    const { match, loggedUser, history, onLogout } = this.props;
    const myRating = [false, false, false, false, false];
    for (let index = 0; index < selectedStar + 1; index++) {
      myRating[index] = true;
    }
    const rating = { givenScore: selectedStar + 1, productId: match.params.id }
    this.setState({
      myRating: myRating,
    });

    setProductRating(rating, loggedUser)
      .then(res => {
        if (!res.ok) { throw res; }
        return res.json();

      }).then(res => {
        this.setState({
          ratingMessage: 'Rating submitted successfully'
        })
      }).catch(err => {
        if (err.status === 404) {
          history.push('/');
        } else if (err.status === 401) {
          history.push('/login');
          Auth.signOut()
            .then(data => onLogout())
            .catch(err => {
              console.error('Unexpected error while loggin out');
              console.error(err);
            });
        } else {
          this.setState({
            ratingMessage: 'There was an error while saving the rating'
          })
          console.error(err);
        }
      })
  }

  handleStarBlur(e) {
    e.preventDefault();

    const { myRating } = this.state;
    this.setState({
      myRatingView: myRating,
    })
  }

  componentDidMount() {
    this.loadProduct();
    this.loadUserRating();
  }

  render() {
    const { amountSelected, name, imageUrl, price, ranking, amount, inStock, description,
      myRatingView, ratingMessage, loading } = this.state;
    const { loggedUser } = this.props;

    return (
      <div className="product-details">

        <section className="product-details__section">

          <div className="product-details__heading-wrapper">
            <div className="product-details__heading-container">
              <h1 className="product-details__heading">{capitalizeName(name)}</h1>
            </div>
          </div>

          {loading ?
            <LoadingScreen />
            :
            <div className="product-details__content-container">
              <div className="product-details__image-wrapper">
                <img src={imageUrl} alt="Product" className="product-details__image" />
              </div>

              <div className="product-details__description-wrapper">

                <div className="product-details__price">${price}</div>
                <div className="product-details__star">
                  {Array.apply(null, { length: ranking }).map((el, i) =>
                    <i key={i} className="fas fa-star"></i>
                  )}
                  {Array.apply(null, { length: (5 - ranking) }).map((el, i) =>
                    <i key={i} className="far fa-star"></i>
                  )}
                </div>
                <div className="product-details__tax">Tax Included.</div>
                <div className="product-details__amount"><strong className="product-details__strong">Size:</strong> {amount}</div>
                <div className="product-details__in-stock">
                  {inStock === 0 ?
                    "There are none left in stock"
                    :
                    `There are ${inStock} left in stock.`
                  }
                </div>
                {loggedUser && inStock > 0 ?
                  <React.Fragment>
                    <div className="product-details__amount-selected-title"><strong className="product-details__strong">How much do you need?</strong></div>
                    <div className="product-details__amount-selected">

                      <button className="product-details__minus-button" onClick={(e) => this.handleChangeAmount(e, -1)}><i className="fas fa-minus"></i></button>
                      <input className="product-details__amount-text" type="text" onChange={(e) => this.handleAmountOnChange(e)}
                        onBlur={(e) => this.handleAmountOnBlur(e)} value={amountSelected} />
                      <button className="product-details__plus-button" onClick={(e) => this.handleChangeAmount(e, 1)}><i className="fas fa-plus"></i></button>

                    </div>
                    {this.isProductInShoppingBag() ?
                      <button onClick={(e) => this.removeFromShoppingBag(e)} className="product-details__button">Take out of bag</button>
                      :
                      <button onClick={(e) => this.addToShoppingBag(e)} className="product-details__button">Add to bag</button>
                    }
                  </React.Fragment>
                  :
                  null
                }
                <div className="product-details__description-title"><strong className="product-details__strong">Description</strong></div>
                <div className="product-details__description">{description}</div>

                {loggedUser ?
                  <React.Fragment>
                    <h2 className="product-details__heading-secondary">What did you think of this product?</h2>
                    <div className="product-details__rating">
                      {myRatingView.map((flag, i) =>
                        <button key={i} onMouseEnter={(e) => this.handleStarSelect(e, i)}
                          onMouseLeave={(e) => this.handleStarBlur(e)}
                          onFocus={(e) => this.handleStarSelect(e, i)}
                          onClick={(e) => this.handleStarClick(e, i)}
                          onBlur={(e) => this.handleStarBlur(e)}
                          className="product-details__rating-button">
                          <i className={`${flag ? "fas" : "far"} fa-star product-details__star`}></i>
                        </button>
                      )}
                    </div>
                    <div className="product-details__rating-message">
                      {ratingMessage}
                    </div>
                  </React.Fragment>
                  : null
                }

              </div>
            </div>
          }
        </section>

      </div>
    )
  }
}

const mapStateToProps = state => ({
  loggedUser: state.userReducer.loggedUser,
  shoppingBag: state.shoppingBagReducer.shoppingBag,
});

const mapDispatchToProps = dispatch => ({
  onAddToBag: (product) => dispatch(addToBag(product)),
  onRemoveFromBag: (product) => dispatch(removeFromBag(product)),
  onLogout: () => { dispatch(logout()); dispatch(clearBag()) },
});

export default withRouter(connect(
  mapStateToProps,
  mapDispatchToProps
)(ProductDetails));
