import io
import json
import time
from time import sleep
import requests
from bs4 import BeautifulSoup
import lxml
import pandas as pd
import numpy as np
import os
import sys

headers = {
    'user-agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.41 Safari/537.36'
}

detail_url = 'https://www.mercari.com/us/item/{}/?ref=category_detail'

url_file_path = os.path.join(os.path.dirname(sys.argv[0]), 'url.txt')
csv_file_path = os.path.join(os.path.dirname(sys.argv[0]), 'data.csv')
error_file_path = os.path.join(os.path.dirname(sys.argv[0]), 'error.txt')

url_file = open(url_file_path)
url_data = url_file.readlines()
max_count = len(url_data)
count = 1
for url in url_data:
    item_str = url.strip().replace('\n','')
    item_json = json.loads(item_str)
    item_id = item_json['item_id']
    item_name = item_json['item_name']
    item_price = item_json['item_price']
    item_url = detail_url.format(item_id)

    print('开始第' + str(count) + ' / ' + str(max_count) + ' --- ' + item_id)
    seller_name = ''
    from_city = ''
    try:
        res = requests.get(url=item_url, headers=headers)
        soup = BeautifulSoup(res.text, 'lxml')
        seller_name = soup.select('.Text__H2-sc-55omqz-2')[0].text.strip().replace('\n', '')
        from_city = soup.select('.components__ShippingText-nzah0l-4')[0].text.split('from')[-1]
        category = soup.select('.product__Category-sc-1bt6c5v-1')[0].text.strip()
    except:
        print('未获得数据,跳过,保存记录')
        with open(error_file_path, 'a') as f:
            f.write(item_str + '\n')
        count += 1
        continue
    # print(seller_name)
    # print(from_city)
    item_info = list()
    item_info.append(item_name)
    item_info.append(item_price)
    item_info.append(seller_name)
    item_info.append(from_city)
    item_info.append(category)

    item_info = np.array(item_info).reshape(-1,5)
    item_result = pd.DataFrame(item_info)
    item_result.to_csv(csv_file_path,mode='a',index=False,encoding='utf-8-sig',header=False)

    count += 1

