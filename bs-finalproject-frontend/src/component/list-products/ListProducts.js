import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import Select from 'react-select';

import './ListProducts.scss';
import { searchProducts } from '../../services/ProductService';
import ProductOverview from '../product-overview/ProductOverview';
import ReactPaginate from 'react-paginate';
import LoadingScreen from '../loading-screen/LoadingScreen';

class ListProducts extends Component {

  constructor(props) {
    super(props);

    this.state = {
      selectedCategory: this.categoryOptions[0],
      selectedSort: this.sortOptions[0],
      products: [],
      totalPages: 0,
      selectedPage: 0,
      category: null,
      sort: null,
      searchTerm: '',
      loading: false,
    }
  }

  sortOptions = [
    { value: 'all', label: 'All Products' },
    { value: 'price,desc', label: 'Price, from high to low' },
    { value: 'price,asc', label: 'Price, from low to high' },
    { value: 'name,asc', label: 'Alphabetically, from A to Z' },
    { value: 'name,desc', label: 'Alphabetically, from Z to A' },
  ];

  categoryOptions = [
    { value: 'all', label: 'All Products' },
    { value: 'fruit', label: 'Fruits' },
    { value: 'vegetable', label: 'Vegetables' },
    { value: 'bean', label: 'Bean' },
    { value: 'dairy', label: 'Dairy' },
    { value: 'poultry', label: 'Poultry' },
    { value: 'meat', label: 'Meat' },
  ];

  handleSortChange = (selectedSort) => {
    const { selectedPage, category, searchTerm } = this.state;
    this.setState({ selectedSort, sort: selectedSort.value });

    this.refreshProducts(selectedPage, category, selectedSort.value, searchTerm);
  }

  handleCategoryChange = (selectedCategory) => {
    const { sort, searchTerm } = this.state;
    this.setState({ selectedCategory, category: selectedCategory.value, selectedPage: 0 });

    this.refreshProducts(0, selectedCategory.value, sort, searchTerm);
  }

  handleSearchTermChange = (e) => {
    e.preventDefault();
    this.setState({ searchTerm: e.target.value });
  }

  handleClearSearchTerm = (e) => {
    e.preventDefault();

    const { selectedPage, category, sort } = this.state;
    this.setState({ searchTerm: '' });
    this.refreshProducts(selectedPage, category, sort, null);
  }

  handleSearch = (e) => {
    e.preventDefault();

    const { sort, category, searchTerm } = this.state;
    this.setState({ selectedPage: 0 });
    this.refreshProducts(0, category, sort, searchTerm);
  }

  handlePageChange = (page) => {
    const { category, sort, searchTerm } = this.state;
    const selectedPage = page.selected;
    this.setState({ selectedPage: selectedPage });
    this.refreshProducts(selectedPage, category, sort, searchTerm);
  }

  refreshProducts(selectedPage, category, sort, searchTerm) {
    this.setState({ loading: true });

    if (sort === 'all') {
      sort = null;
    }

    if (category === 'all') {
      category = null;
    }

    searchProducts(selectedPage, category, sort, searchTerm)
      .then(response => {
        if (!response.ok) { throw response }
        return response.json();
      })
      .then(response => {
        this.setState({
          products: response.response.content,
          totalPages: response.response.totalPages,
          loading: false,
        })
      })
      .catch(error => {
        console.error('Unexpected error');
        console.error(error);
      })
  }

  componentDidMount() {
    const { searchTerm } = this.state;
    this.refreshProducts(0, null, null, searchTerm);
  }

  render() {
    const { selectedCategory, products, totalPages, selectedPage, selectedSort, searchTerm, loading } = this.state;

    return (
      <div className="list-products">
        <section className="list-products__section">
          <div className="list-products__heading-wrapper">
            <div className="list-products__heading-container">
              <h1 className="list-products__heading">Product Catalog</h1>
            </div>
          </div>

          <div className="list-products__search-wrapper">
            <div className="list-products__search-container">
              <div className="list-products__select-wrapper">
                <label className="list-products__label">Category</label>
                <Select
                  className="list-products__select"
                  classNamePrefix="list-products__select"
                  value={selectedCategory}
                  onChange={this.handleCategoryChange}
                  options={this.categoryOptions}
                />
              </div>

              <div className="list-products__select-wrapper">
                <label className="list-products__label">Sort By</label>
                <Select
                  className="list-products__select"
                  classNamePrefix="list-products__select"
                  value={selectedSort}
                  onChange={this.handleSortChange}
                  options={this.sortOptions}
                />
              </div>

              <form className="list-products__search-bar-wrapper">
                <label htmlFor="search" className="list-products__label--search-bar">Search a Product</label>
                <input type="text" name="search" id="search"
                  value={searchTerm} onChange={(e) => this.handleSearchTermChange(e)}
                  className="list-products__search-bar" placeholder="Search a product" />
                <button onClick={e => this.handleSearch(e)} className="list-products__search-button">
                  <i className="fas fa-search"></i>
                </button>
                <button onClick={e => this.handleClearSearchTerm(e)} className="list-products__search-button">
                  <i className="fas fa-times"></i>
                </button>
              </form>
            </div>
          </div>

          {loading ?
            <LoadingScreen />
            :
            <div className="list-products__catalog">
              {products.map((product, i) =>
                <div className="list-products__product-wrapper" key={i}>
                  <ProductOverview product={product} />
                </div>
              )}
            </div>

          }
          <div className={`list-products__paginate ${loading ? 'list-products__hidden' : null}`}>
            <ReactPaginate
              previousLabel={<i className="fas fa-chevron-left"></i>}
              pageRangeDisplayed={1}
              marginPagesDisplayed={1}
              breakLabel={"..."}
              breakLinkClassName={"list-products__paginate-button"}
              previousLinkClassName={"list-products__arrow list-products__arrow--previous"}
              nextLinkClassName={"list-products__arrow list-products__arrow--next"}
              nextLabel={<i className="fas fa-chevron-right"></i>}
              pageCount={totalPages}
              initialPage={selectedPage}
              forcePage={selectedPage}
              pageLinkClassName={"list-products__paginate-button"}
              activeLinkClassName={"list-products__paginate-button--activate"}
              onPageChange={this.handlePageChange}
            />
          </div>
        </section>
      </div >
    )
  }
}

export default withRouter(ListProducts);
