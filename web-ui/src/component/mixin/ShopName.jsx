import { Button, Modal, Form, Input, Radio } from 'antd';
const FormItem = Form.Item;
import React from 'react';
const CollectionCreateForm = Form.create()(
    (props) => {
        const { visible, onCancel, onCreate, form } = props;
        const { getFieldDecorator } = form;
        return (
            <Modal
                visible={visible}
                title="添加新商场"
                okText="添加"
                onCancel={onCancel}
                onOk={onCreate}
            >
                <Form layout="vertical">
                    <FormItem label="商场名称">
                        {getFieldDecorator('shop_name', {
                            rules: [{ required: true, message: '请输入商场名称' }],
                        })(
                            <Input />
                        )}
                    </FormItem>
                    <FormItem label="商场地址">
                        {getFieldDecorator('shop_addr')(<Input type="text" />)}
                    </FormItem>
                    <FormItem label="商场联系人">
                        {getFieldDecorator('shop_manager')(<Input type="text" />)}
                    </FormItem>
                    <FormItem label="联系方式">
                        {getFieldDecorator('shop_telephone')(<Input type="text" />)}
                    </FormItem>
                    <FormItem label="描述">
                        {getFieldDecorator('shop_description')(<Input type="textarea" />)}
                    </FormItem>
                </Form>
            </Modal>
        );
    }
);

export default CollectionCreateForm;