
# coding: utf-8



# In[24]:

from TwitterAPI import TwitterAPI, TwitterOAuth, TwitterRestPager



# In[27]:

# Using OAuth1...
twitter = TwitterAPI("ugPhK07v9CVmAXxQBgNxFg2GL",
                 "bZQWfuZcX7CLE2rV6lKFpQrZPcab3afxgMfYKxhBC4BeRm1tVx",
                 '1602972570-QDSltCStbP5mkAKriUoUTQu8zzDu5YoV0PrNEQ7',
                 'CgmhOhP49G7uzKt9cNcVkV1l9uGdIt8vwxmWaV9uVhQOK')






# In[50]:

# Get BestBuyDeals timeline = ''
# screen_name = 'BestBuy_Deals'
screen_name = "HotelDealsBTD"
timeline = [tweet for tweet in twitter.request('statuses/user_timeline',
                                                {'screen_name': screen_name,
                                                 'exclude_replies': 'true',
                                                 'count': 200})]
print 'got %d tweets for user %s' % (len(timeline), screen_name)


# In[51]:

# Print time got created.

timeline[3]['created_at']


# In[52]:

# Print the text.
print '\n\n\n'.join(t['text'] for t in timeline)


# In[53]:

# Count words
from collections import Counter  # This is just a fancy dict mapping from object->int, starting at 0.
counts = Counter()
for tweet in timeline:
    counts.update(tweet['text'].lower().split())
print('found %d unique terms in %d tweets' % (len(counts), len(timeline)))
counts.most_common(10)


# In[54]:

import re
for tweet in timeline:
    deal = tweet['text']
    print deal + '\n'


# # Find  the deals in the BestBuy_Deal tweets  that match products in BestDeal MySQL product table

# In[55]:

import re

dealMatchGauranteed=[]

for tweet in timeline:
    deal = (tweet['text']).encode('ascii','ignore')
    if (len(re.findall('NY|Miami|Houston|Boston',deal)) >= 1):
        dealMatchGauranteed = dealMatchGauranteed + [deal]
            




# # Create and write the deals into DealMatches.txt file that will be used by web-app of BestDeal to display two deal matches

# In[58]:

dealMatchFile = open('/Users/junyipeng/apache-tomcat-7.0.34/webapps/Hotel/DealMatches.txt', 'w')

for deal in dealMatchGauranteed:
  dealMatchFile.write("%s\n" % deal)

dealMatchFile.close()





