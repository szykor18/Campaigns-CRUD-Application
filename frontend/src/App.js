import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import Select from 'react-select';
import 'bootstrap/dist/css/bootstrap.min.css';

class App extends Component {
  state = {
    campaigns: [],
    editingCampaign: null,
    keywordsOptions: [
      { value: 'keyword1', label: 'Keyword 1' },
      { value: 'keyword2', label: 'Keyword 2' },
    ],
    townsOptions: [
      { value: 'Town1', label: 'Town1' },
      { value: 'Town2', label: 'Town2' },
    ],
    selectedKeywords: [],
    selectedTown: null,
  };
  customSelectStyles = {
    option: (provided, state) => ({
      ...provided,
      color: state.isSelected ? 'white' : 'black', // Text color for options
      backgroundColor: state.isSelected ? 'blue' : 'white', // Background color for options
    }),
  };

  async componentDidMount() {
    this.loadCampaigns();
    const keywordsOptions = await this.loadKeywords();
    const townsOptions = await this.loadTowns();
    this.setState({ keywordsOptions, townsOptions });
  }

  loadCampaigns = async () => {
    try {
      const response = await fetch('/campaigns');
      const campaigns = await response.json();
      this.setState({ campaigns });
    } catch (error) {
      console.error('Error loading campaigns:', error);
    }
  }
  loadKeywords = async () => {
    // Fetch keywords from your backend or define them here
    return [
      { value: 'technology', label: 'Technology' },
      { value: 'marketing', label: 'Marketing' },
      { value: 'business', label: 'Business' },
    ];
  }
  loadTowns = async () => {
    // Fetch towns from your backend or define them here
    return [
      { value: 'cracow', label: 'Cracow' },
      { value: 'warsaw', label: 'Warsaw' },
      { value: 'katowice', label: 'Katowice' },
    ];
  }

  addCampaign = async (campaign) => {
    try {
      await fetch('/campaigns', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(campaign)
      });
      this.loadCampaigns(); // Reload campaigns
    } catch (error) {
      console.error('Error adding campaign:', error);
    }
  }

  updateCampaign = async (id, campaign) => {
    try {
      await fetch(`/campaigns/${id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(campaign)
      });
      this.loadCampaigns(); // Reload campaigns
    } catch (error) {
      console.error('Error updating campaign:', error);
    }
  }

  deleteCampaign = async (id) => {
    try {
      await fetch(`/campaigns/${id}`, {
        method: 'DELETE',
      });
      this.loadCampaigns(); // Reload campaigns
    } catch (error) {
      console.error('Error deleting campaign:', error);
    }
  }

  setEditingCampaign = (campaign) => {
    this.setState({ 
      editingCampaign: campaign,
      selectedKeywords: campaign.keywords.split(',').map(kw => ({ value: kw, label: kw })),
    });
  };

  handleFormSubmit = (event) => {
    event.preventDefault();
    const formData = new FormData(event.target);
    const { selectedKeywords, selectedTown } = this.state;

    const campaign = {
      campaignName: formData.get('campaignName'),
      keywords: selectedKeywords.map(kw => kw.value).join(','),
      bidAmount: formData.get('bidAmount'),
      campaignFund: formData.get('campaignFund'),
      status: formData.get('status'),
      town: selectedTown ? selectedTown.value : '',
      radius: parseFloat(formData.get('radius')),
    };

    if (this.state.editingCampaign) {
      this.updateCampaign(this.state.editingCampaign.id, campaign);
    } else {
      this.addCampaign(campaign);
    }
    event.target.reset();
    this.setState({
        editingCampaign: null,
        selectedKeywords: [],
        selectedTown: null
    });
    this.setState({ editingCampaign: null, selectedKeywords: [], selectedTown: null });
  };

  handleKeywordsChange = (selectedOptions) => {
    this.setState({ selectedKeywords: selectedOptions });
  };

  handleTownChange = (selectedOption) => {
    this.setState({ selectedTown: selectedOption });
  };

  renderCampaignForm() {
    const { editingCampaign, keywordsOptions, townsOptions, selectedKeywords, selectedTown } = this.state;

    return (
      <form onSubmit={this.handleFormSubmit}>
        <hr></hr>
        <input type="text" name="campaignName" placeholder="Campaign Name" defaultValue={editingCampaign ? editingCampaign.campaignName : ''} required />
        <Select
          options={keywordsOptions}
          isMulti
          required
          value={selectedKeywords}
          onChange={this.handleKeywordsChange}
          placeholder="Select Keywords"
          styles={this.customSelectStyles}
        />

        <input type="number" name="bidAmount" placeholder="Bid Amount" min="1" defaultValue={editingCampaign ? editingCampaign.bidAmount : ''} required />
        <input type="number" name="campaignFund" placeholder="Campaign Fund" defaultValue={editingCampaign ? editingCampaign.campaignFund : ''} required />

        <select name="status" defaultValue={editingCampaign ? editingCampaign.status : 'ON'}>
          <option value="ON">On</option>
          <option value="OFF">Off</option>
        </select>

        <Select
          options={townsOptions}
          value={selectedTown}
          required
          onChange={this.handleTownChange}
          placeholder="Select Town"
          styles={this.customSelectStyles}
        />

        <input 
          type="text" 
          name="radius" 
          placeholder="Radius" 
          defaultValue={editingCampaign ? editingCampaign.radius : ''} 
          pattern="^\d*(\.\d+)?$" // Pattern to allow decimals
          title="Please enter a valid decimal number"
          required 
        />
        <button type="submit">{editingCampaign ? 'Update' : 'Add'} Campaign</button>
      </form>
    );
  }

  render() {
    const { campaigns } = this.state;
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <div className="App-intro">
            <h2>Campaigns List</h2>
            <hr></hr>
            {campaigns.map(campaign =>
              <div key={campaign.id}>
                {campaign.campaignName} - {campaign.keywords}
                <button onClick={() => this.setEditingCampaign(campaign)}>Edit</button>
                <button onClick={() => this.deleteCampaign(campaign.id)}>Delete</button>
              </div>
            )}
            {this.renderCampaignForm()}
          </div>
        </header>
      </div>
    );
  }
}

export default App;